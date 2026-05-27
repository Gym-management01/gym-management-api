package com.example.gymmanagement.repository;

import com.example.gymmanagement.model.Pagamento;
import com.example.gymmanagement.model.PagamentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByAlunoId(Long alunoId);

    List<Pagamento> findByMatriculaId(Long matriculaId);

    List<Pagamento> findByStatus(PagamentoStatus status);

    List<Pagamento> findByStatusAndVencimentoBefore(PagamentoStatus status, LocalDate vencimento);
}
