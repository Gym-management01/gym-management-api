package com.example.demo.controller;

import com.example.demo.dto.Request.FuncionarioRequestDto;
import com.example.demo.dto.Response.FuncionarioResponseDto;
import com.example.demo.model.Funcionario;
import com.example.demo.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/funcionarios")
@RestController
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<FuncionarioResponseDto> criarFuncionario(@RequestBody @Valid FuncionarioRequestDto funcionarioRequestDto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioRequestDto.nome());
        funcionario.setCargo(funcionarioRequestDto.cargo());
        funcionario.setTelefone(funcionarioRequestDto.telefone());
        funcionario.setCpf(funcionarioRequestDto.cpf());
        funcionario.setSalario(funcionarioRequestDto.salario());
        funcionario.setUsername(funcionarioRequestDto.username());
        funcionario.setPassword(funcionarioRequestDto.password());

        Funcionario novoFuncionario = funcionarioService.criarFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new FuncionarioResponseDto(novoFuncionario));
        }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDto>> listarFuncionarios() {
        List<FuncionarioResponseDto> resposta = funcionarioService.listarFuncionarios().stream()
                .map(FuncionarioResponseDto::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDto> buscarFuncionario(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);
        return ResponseEntity.ok(new FuncionarioResponseDto(funcionario));
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
        funcionarioService.deletarFuncionario(id);
        return ResponseEntity.noContent().build();
    }


}
