package com.example.demo.dto.Request;

import com.example.demo.model.Plano;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


public record AlunoRequestDto(
        @NotBlank(message = "O nome do aluno é obrigatório")
        @Size(max = 100, message = "O nome do aluno deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "O telefone do aluno é obrigatório")
        @Pattern(regexp = "\\d{8,14}", message = "Telefone deve conter de 8 a 14 dígitos")
        String telefone,

        @NotBlank(message = "O cpf do aluno é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        @NotNull(message = "O plano do aluno é obrigatório")
        Plano plano,

        @NotNull(message = "A mensalidade é obrigatória")
        @Positive(message = "A mensalidade deve ser positiva")
        BigDecimal mensalidade
) {}