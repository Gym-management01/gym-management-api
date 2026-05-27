package com.example.gymmanagement.dto.Request;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDto(
        @NotBlank(message = "O usuário é obrigatório")
        String username,
        @NotBlank(message = "A senha é obrigatória")
        String password
) {
}

