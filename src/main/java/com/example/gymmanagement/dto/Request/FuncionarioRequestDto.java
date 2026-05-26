package com.example.demo.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import com.example.demo.model.UserRole;

public record FuncionarioRequestDto(
        @NotBlank(message = "O nome do funcionário é obrigatório")
        @Size(max = 100, message = "O nome do funcionário deve ter no máximo 100 caracteres")
        String nome,

        @NotNull(message = "O cargo do funcionário é obrigatório")
        UserRole cargo,

        @NotBlank(message = "O telefone do funcionário é obrigatório")
        @Pattern(regexp = "\\d{8,14}", message = "Telefone deve conter de 8 a 14 dígitos")
        String telefone,

        @NotBlank(message = "O cpf do funcionário é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        @NotNull(message = "O salário do funcionário é obrigatório")
        @Positive(message = "O salário deve ser positivo")
        BigDecimal salario,

        @NotBlank(message = "O username é obrigatório")
        @Size(max = 100, message = "O username deve ter no máximo 100 caracteres")
        String username,

        @NotBlank(message = "A senha é obrigatória")
        String password
) {}