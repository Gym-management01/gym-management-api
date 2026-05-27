package com.example.gymmanagement.dto.Response;

import com.example.gymmanagement.model.Aluno;
import com.example.gymmanagement.model.Plano;
import java.math.BigDecimal;

public record AlunoResponseDto(
        Long id,
        String nome,
        String telefone,
        BigDecimal mensalidade,
        Plano plano,
        String cpf
) {
    // Esse construtor facilita MUITO a nossa vida na hora de converter!
    public AlunoResponseDto(Aluno aluno) {
        this(aluno.getId(), aluno.getNome(), aluno.getTelefone(), aluno.getMensalidade(), aluno.getPlano(),aluno.getCpf());
    }
}