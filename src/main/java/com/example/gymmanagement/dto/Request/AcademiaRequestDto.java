package com.example.demo.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record AcademiaRequestDto(
        @NotBlank(message = "O nome da academia é obrigatório")
        @Size(max = 100, message = "O nome da academia deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "O endereço da academia é obrigatório")
        @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres")
        String endereco,

        @NotBlank(message = "O telefone da academia é obrigatório")
        @Pattern(regexp = "\\d{8,14}", message = "Telefone deve conter de 8 a 14 dígitos")
        String telefone,

        @NotBlank(message = "O cnpj da academia é obrigatório")
        @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos")
        String cnpj
) {}
