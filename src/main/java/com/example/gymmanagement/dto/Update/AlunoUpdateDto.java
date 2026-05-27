package com.example.gymmanagement.dto.Update;

import com.example.gymmanagement.model.Plano;

import java.math.BigDecimal;

public record AlunoUpdateDto(
        String nome,
        String telefone,
        BigDecimal mensalidade,
        Plano plano,
        String cpf
) {
}
