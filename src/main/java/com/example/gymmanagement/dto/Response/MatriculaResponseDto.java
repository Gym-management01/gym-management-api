package com.example.demo.dto.Response;

import com.example.demo.model.Matricula;
import com.example.demo.model.Plano;
import java.time.LocalDate;
public record MatriculaResponseDto(
        Long id,
        Long alunoId,
        String nomeAluno, // Pegamos só o nome para facilitar o front-end
        Plano plano,
        LocalDate dataInicio,
        LocalDate dataFim,
        Boolean ativa
) {
    // Construtor mágico para converter a Entidade no DTO
    public MatriculaResponseDto(Matricula matricula) {
        this(
                matricula.getId(),
                matricula.getAluno().getId(),
                matricula.getAluno().getNome(),
                matricula.getPlano(),
                matricula.getDataInicio(),
                matricula.getDataFim(),
                matricula.getAtiva()
        );
    }
}