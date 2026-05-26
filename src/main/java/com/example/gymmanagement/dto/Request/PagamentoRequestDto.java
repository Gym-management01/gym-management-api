package com.example.demo.dto.Request;

import com.example.demo.model.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoRequestDto(
        @NotNull(message = "O id do aluno é obrigatório")
        Long alunoId,
        Long matriculaId,
        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser positivo")
        BigDecimal valor,
        @NotNull(message = "O vencimento é obrigatório")
        LocalDate vencimento,
        @NotNull(message = "A forma de pagamento é obrigatória")
        FormaPagamento forma
) {
}

