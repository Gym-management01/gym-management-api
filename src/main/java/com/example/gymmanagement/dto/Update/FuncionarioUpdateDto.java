package com.example.gymmanagement.dto.Update;

import com.example.gymmanagement.model.UserRole;

public record FuncionarioUpdateDto(
        UserRole cargo,
        String telefone
) {
}
