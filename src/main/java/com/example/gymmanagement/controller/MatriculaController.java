package com.example.demo.controller;

import com.example.demo.dto.Request.MatriculaRequestDto;
import com.example.demo.dto.Response.MatriculaResponseDto;
import com.example.demo.model.Aluno;
import com.example.demo.model.Matricula;
import com.example.demo.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping ("/matriculas")
@RestController

public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<MatriculaResponseDto> criarMatricula(@RequestBody @Valid MatriculaRequestDto matriculaRequestDto) {
        Matricula matricula = new Matricula();
        Aluno aluno = new Aluno();
        aluno.setId(matriculaRequestDto.alunoId());
        matricula.setAluno(aluno);
        matricula.setPlano(matriculaRequestDto.plano());
        matricula.setDataInicio(matriculaRequestDto.dataInicio());
        matricula.setDataFim(matriculaRequestDto.dataFim());
        matricula.setAtiva(matriculaRequestDto.ativa());

        Matricula novaMatricula = matriculaService.criarMatricula(matricula);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MatriculaResponseDto(novaMatricula));
    }

    @GetMapping
    public ResponseEntity<List<MatriculaResponseDto>> listarMatriculas() {
        List<MatriculaResponseDto> resposta = matriculaService.listarMatriculas().stream()
                .map(MatriculaResponseDto::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<MatriculaResponseDto> buscarMatricula(@PathVariable Long id) {
        Matricula matricula = matriculaService.buscarMatricula(id);
        return ResponseEntity.ok(new MatriculaResponseDto(matricula));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deletarMatricula(@PathVariable Long id) {
        matriculaService.deletarMatricula(id);
        return ResponseEntity.noContent().build();
    }

}
