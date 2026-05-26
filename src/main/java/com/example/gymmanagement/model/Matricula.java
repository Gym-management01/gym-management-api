package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "matriculas")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Plano plano;

    @Column (nullable = false)
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @Column (nullable = false)
    private Boolean ativa;
}