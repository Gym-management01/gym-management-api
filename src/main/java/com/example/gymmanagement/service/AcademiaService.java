package com.example.demo.service;

import com.example.demo.model.Academia;
import com.example.demo.repository.AcademiaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AcademiaService {

    private final AcademiaRepository academiaRepository;

    public AcademiaService(AcademiaRepository academiaRepository) {
        this.academiaRepository = academiaRepository;
    }

    public Academia criarAcademia(Academia academia) {
        validarAcademia(academia);
        return academiaRepository.save(academia);
    }

    public Academia buscarAcademia(Long id) {
        return academiaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Academia não encontrada."));
    }

    public List<Academia> listarAcademias() {
        return academiaRepository.findAll();
    }

    public void deletarAcademia(Long id){
        if (!academiaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Academia não encontrada.");
        }
        academiaRepository.deleteById(id);
    }

    private void validarAcademia(Academia academia) {
        if (academia.getNome() == null || academia.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome da academia é obrigatório.");
        }
        if (academia.getEndereco() == null || academia.getEndereco().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço é obrigatório.");
        }
        if (academia.getTelefone() == null || academia.getTelefone().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone inválido.");
        }
    }
}