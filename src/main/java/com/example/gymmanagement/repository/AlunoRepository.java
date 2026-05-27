package com.example.gymmanagement.repository;
import com.example.gymmanagement.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}