package com.example.gymmanagement.dto.Response;

import com.example.gymmanagement.model.Funcionario;

import java.math.BigDecimal;
import com.example.gymmanagement.model.UserRole;

public record FuncionarioResponseDto(
        Long id,
        String nome,
        UserRole cargo,
        String telefone,
        String cpf,
        BigDecimal salario,
        String username
) {
    public FuncionarioResponseDto(Funcionario funcionario) {
        this(funcionario.getId(), funcionario.getNome(), funcionario.getCargo(), funcionario.getTelefone(), funcionario.getCpf(), funcionario.getSalario(), funcionario.getUsername());
    }
}
