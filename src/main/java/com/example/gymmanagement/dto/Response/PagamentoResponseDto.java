package com.example.demo.dto.Response;

import com.example.demo.model.FormaPagamento;
import com.example.demo.model.Pagamento;
import com.example.demo.model.PagamentoStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoResponseDto(
        Long id,
        Long alunoId,
        Long matriculaId,
        BigDecimal valor,
        LocalDate vencimento,
        LocalDate dataPagamento,
        PagamentoStatus status,
        FormaPagamento forma
) {
    public PagamentoResponseDto(Pagamento pagamento) {
        this(
                pagamento.getId(),
                pagamento.getAluno().getId(),
                pagamento.getMatricula() != null ? pagamento.getMatricula().getId() : null,
                pagamento.getValor(),
                pagamento.getVencimento(),
                pagamento.getDataPagamento(),
                pagamento.getStatus(),
                pagamento.getForma()
        );
    }
}

