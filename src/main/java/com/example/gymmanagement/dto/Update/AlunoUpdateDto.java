package com.example.demo.dto.Update;

import com.example.demo.model.Plano;

import java.math.BigDecimal;

public record AlunoUpdateDto(
        String nome,
        String telefone,
        BigDecimal mensalidade,
        Plano plano,
        String cpf
) {
}
