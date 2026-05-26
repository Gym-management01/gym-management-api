package com.example.demo.dto.Response;

import com.example.demo.model.Aluno;
import com.example.demo.model.Plano;
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