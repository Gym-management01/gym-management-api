package com.example.demo.service;

import com.example.demo.model.Funcionario;
import com.example.demo.repository.FuncionarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Funcionario criarFuncionario(Funcionario funcionario) {
        validarFuncionario(funcionario);
        funcionario.setPassword(passwordEncoder.encode(funcionario.getPassword()));
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario buscarFuncionario(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado."));
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }


    public void deletarFuncionario(Long id){
        if(!funcionarioRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado.");
        }
        funcionarioRepository.deleteById(id);
    }

    private void validarFuncionario(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do funcionário é obrigatório.");
        }
        if (funcionario.getTelefone() == null || funcionario.getTelefone().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone inválido.");
        }
        if (funcionario.getSalario() == null || funcionario.getSalario().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Salário deve ser positivo.");
        }
    }
}