package com.example.demo.dto.Update;

import com.example.demo.model.UserRole;

public record FuncionarioUpdateDto(
        UserRole cargo,
        String telefone
) {
}
