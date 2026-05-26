package com.example.demo.service;

import com.example.demo.dto.Request.PagamentoPagamentoDto;
import com.example.demo.dto.Request.PagamentoRequestDto;
import com.example.demo.dto.Response.PagamentoResponseDto;
import com.example.demo.model.Aluno;
import com.example.demo.model.Matricula;
import com.example.demo.model.Pagamento;
import com.example.demo.model.PagamentoStatus;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.repository.MatriculaRepository;
import com.example.demo.repository.PagamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final AlunoRepository alunoRepository;
    private final MatriculaRepository matriculaRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository,
                            AlunoRepository alunoRepository,
                            MatriculaRepository matriculaRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.alunoRepository = alunoRepository;
        this.matriculaRepository = matriculaRepository;
    }

    public PagamentoResponseDto criarPagamento(PagamentoRequestDto request) {
        Aluno aluno = alunoRepository.findById(request.alunoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado."));

        Matricula matricula = null;
        if (request.matriculaId() != null) {
            matricula = matriculaRepository.findById(request.matriculaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada."));
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setAluno(aluno);
        pagamento.setMatricula(matricula);
        pagamento.setValor(request.valor());
        pagamento.setVencimento(request.vencimento());
        pagamento.setForma(request.forma());
        pagamento.setStatus(PagamentoStatus.PENDENTE);

        Pagamento salvo = pagamentoRepository.save(pagamento);
        return new PagamentoResponseDto(salvo);
    }

    public PagamentoResponseDto buscarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento não encontrado."));
        return new PagamentoResponseDto(pagamento);
    }

    public List<PagamentoResponseDto> listarPagamentos(Long alunoId, Long matriculaId, PagamentoStatus status) {
        List<Pagamento> pagamentos;
        if (alunoId != null) {
            pagamentos = pagamentoRepository.findByAlunoId(alunoId);
        } else if (matriculaId != null) {
            pagamentos = pagamentoRepository.findByMatriculaId(matriculaId);
        } else if (status != null) {
            pagamentos = pagamentoRepository.findByStatus(status);
        } else {
            pagamentos = pagamentoRepository.findAll();
        }

        return pagamentos.stream().map(PagamentoResponseDto::new).toList();
    }

    public PagamentoResponseDto pagar(Long id, PagamentoPagamentoDto request) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento não encontrado."));

        LocalDate dataPagamento = request != null ? request.dataPagamento() : null;
        pagamento.setDataPagamento(dataPagamento != null ? dataPagamento : LocalDate.now());
        pagamento.setStatus(PagamentoStatus.PAGO);

        Pagamento salvo = pagamentoRepository.save(pagamento);
        return new PagamentoResponseDto(salvo);
    }

    public List<PagamentoResponseDto> atualizarAtrasados() {
        List<Pagamento> pendentesVencidos = pagamentoRepository
                .findByStatusAndVencimentoBefore(PagamentoStatus.PENDENTE, LocalDate.now());

        pendentesVencidos.forEach(pagamento -> pagamento.setStatus(PagamentoStatus.ATRASADO));
        List<Pagamento> atualizados = pagamentoRepository.saveAll(pendentesVencidos);
        return atualizados.stream().map(PagamentoResponseDto::new).toList();
    }
}
