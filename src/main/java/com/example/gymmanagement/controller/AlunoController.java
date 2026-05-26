package com.example.demo.controller;

import com.example.demo.dto.Request.AlunoRequestDto;
import com.example.demo.dto.Response.AlunoResponseDto;
import com.example.demo.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // Apenas UM método POST. Recebe o Request DTO e devolve o Response DTO.
    @PostMapping
    public ResponseEntity<AlunoResponseDto> cadastrarAluno(@RequestBody @Valid AlunoRequestDto alunoRequestDto) {
        AlunoResponseDto response = alunoService.cadastrarAluno(alunoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Busca protegida: não devolve a Entidade, devolve o DTO
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDto> buscarAluno(@PathVariable Long id) {
        AlunoResponseDto response = alunoService.buscarAluno(id);
        return ResponseEntity.ok(response);
    }

    // Listagem protegida: devolve uma lista de DTOs, não de Entidades
    @GetMapping
    public ResponseEntity<List<AlunoResponseDto>> listarAlunos() {
        List<AlunoResponseDto> listaAlunos = alunoService.listarAlunos();
        return ResponseEntity.ok(listaAlunos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }
}