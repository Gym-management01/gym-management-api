package com.example.gymmanagement.dto.Response;

public record AuthResponseDto(
        String token,
        String tokenType
) {
}

