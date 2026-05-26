package com.example.demo.dto.Response;
import com.example.demo.model.Academia;
public record AcademiaResponseDto(
        Long id,
        String nome,
        String endereco,
        String telefone,
        String cnpj
) {
    public AcademiaResponseDto(Academia academia) {
        this(academia.getId(), academia.getNome(), academia.getEndereco(), academia.getTelefone(), academia.getCnpj());
    }
}
