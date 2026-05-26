package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true)
    private String telefone;

    @Column (nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false)
    private BigDecimal mensalidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Plano plano;

}
