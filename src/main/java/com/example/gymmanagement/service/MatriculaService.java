package com.example.gymmanagement.service;

import com.example.gymmanagement.model.Aluno;
import com.example.gymmanagement.model.FormaPagamento;
import com.example.gymmanagement.model.Matricula;
import com.example.gymmanagement.model.Pagamento;
import com.example.gymmanagement.model.PagamentoStatus;
import com.example.gymmanagement.repository.AlunoRepository;
import com.example.gymmanagement.repository.MatriculaRepository;
import com.example.gymmanagement.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MatriculaService {


    private final AlunoRepository alunoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final MatriculaRepository matriculaRepository;

    public MatriculaService(AlunoRepository alunoRepository,MatriculaRepository matriculaRepository, PagamentoRepository pagamentoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.alunoRepository = alunoRepository;
    }

    public Matricula criarMatricula(Matricula matricula) {

        if (matricula.getAluno() == null || matricula.getAluno().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno é obrigatório.");
        }

        Aluno aluno = alunoRepository.findById(matricula.getAluno().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        matricula.setAluno(aluno);

        validarMatricula(matricula);

        Matricula matriculaSalva = matriculaRepository.save(matricula);

        Pagamento pagamento = new Pagamento();
        pagamento.setAluno(aluno);
        pagamento.setMatricula(matriculaSalva);
        pagamento.setValor(aluno.getMensalidade());
        pagamento.setVencimento(matriculaSalva.getDataInicio());
        pagamento.setStatus(PagamentoStatus.PENDENTE);
        pagamento.setForma(FormaPagamento.PIX);
        pagamentoRepository.save(pagamento);

        return matriculaSalva;
    }


    public List<Matricula> listarMatriculas() {
        return matriculaRepository.findAll();
    }

    public Matricula buscarMatricula(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada."));
    }

    public void deletarMatricula(Long id) {
        if (!matriculaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada.");
        }
        matriculaRepository.deleteById(id);
    }

    private void validarMatricula(Matricula matricula) {
        if (matricula.getAluno() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno é obrigatório.");
        }
        if (matricula.getPlano() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plano é obrigatório.");
        }
        if (matricula.getDataInicio() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de início é obrigatória.");
        }
        if (matricula.getDataFim() != null && matricula.getDataFim().isBefore(matricula.getDataInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de fim não pode ser antes da data de início.");
        }
        if (matricula.getAtiva() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status de matrícula (ativa) é obrigatório.");
        }
    }
}