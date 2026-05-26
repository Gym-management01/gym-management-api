package com.example.demo.dto.Request;

import com.example.demo.model.Plano;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record MatriculaRequestDto(
        @NotNull(message = "O id do aluno é obrigatório")
        Long alunoId,
        @NotNull( message = "O plano do aluno é obrigatório")
        Plano plano,
        @NotNull(message = "A data de início é obrigatória")
        LocalDate dataInicio,
        LocalDate dataFim,
        @NotNull(message = "O status de matrícula (ativa) é obrigatório")
        Boolean ativa

) {
}
