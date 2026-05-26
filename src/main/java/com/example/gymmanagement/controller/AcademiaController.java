package com.example.demo.controller;

import com.example.demo.dto.Request.AcademiaRequestDto;
import com.example.demo.dto.Response.AcademiaResponseDto;
import com.example.demo.model.Academia;
import com.example.demo.service.AcademiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academias")
public class AcademiaController {
    @Autowired
    private AcademiaService academiaService;

    @PostMapping
    public ResponseEntity<AcademiaResponseDto> criarAcademia(@RequestBody @Valid AcademiaRequestDto academiaRequestDto) {
        Academia academia = new Academia();
        academia.setNome(academiaRequestDto.nome());
        academia.setEndereco(academiaRequestDto.endereco());
        academia.setTelefone(academiaRequestDto.telefone());
        academia.setCnpj(academiaRequestDto.cnpj());

        Academia novaAcademia = academiaService.criarAcademia(academia);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AcademiaResponseDto(novaAcademia));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademiaResponseDto> buscarAcademia(@PathVariable Long id) {
        Academia academia = academiaService.buscarAcademia(id);
        return ResponseEntity.ok(new AcademiaResponseDto(academia));
    }

    @GetMapping
    public ResponseEntity<List<AcademiaResponseDto>> listarAcademias() {
        List<AcademiaResponseDto> resposta = academiaService.listarAcademias().stream()
                .map(AcademiaResponseDto::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAcademia(@PathVariable Long id) {
        academiaService.deletarAcademia(id);
        return ResponseEntity.noContent().build();
    }
}
