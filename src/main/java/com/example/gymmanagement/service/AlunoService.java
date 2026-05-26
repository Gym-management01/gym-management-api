package com.example.demo.service;

import com.example.demo.dto.Request.AlunoRequestDto;
import com.example.demo.dto.Response.AlunoResponseDto;
import com.example.demo.model.Aluno;
import com.example.demo.repository.AlunoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    // Retorna o Response DTO
    public AlunoResponseDto buscarAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado."));

        return new AlunoResponseDto(aluno);
    }

    // Retorna uma Lista de Response DTOs
    public List<AlunoResponseDto> listarAlunos() {
        return alunoRepository.findAll().stream()
                .map(AlunoResponseDto::new)
                .toList();
    }

    // Não retorna nada, só apaga do banco
    public void deletarAluno(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado.");        }
        alunoRepository.deleteById(id);
    }

    // Recebe o Request DTO e retorna o Response DTO
    public AlunoResponseDto cadastrarAluno(AlunoRequestDto alunoRequestDto) {
        Aluno aluno = new Aluno();
        aluno.setNome(alunoRequestDto.nome());
        aluno.setTelefone(alunoRequestDto.telefone());
        aluno.setMensalidade(alunoRequestDto.mensalidade());
        aluno.setPlano(alunoRequestDto.plano());
        aluno.setCpf(alunoRequestDto.cpf());

        validarAluno(aluno);

        Aluno alunoSalvo = alunoRepository.save(aluno);

        return new AlunoResponseDto(alunoSalvo);
    }


    private void validarAluno(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do aluno é obrigatório.");
        }
        if (aluno.getTelefone() == null || aluno.getTelefone().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone inválido.");
        }
        if (aluno.getMensalidade() == null || aluno.getMensalidade().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mensalidade deve ser positiva.");
        }
        if (aluno.getPlano() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plano é obrigatório.");
        }
    }
}