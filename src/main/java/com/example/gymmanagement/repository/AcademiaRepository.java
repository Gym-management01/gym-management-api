package com.example.demo.repository;
import com.example.demo.model.Academia;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AcademiaRepository extends JpaRepository<Academia, Long>  {
}
