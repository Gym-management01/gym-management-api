package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "academias")
public class Academia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 100)
    private String nome;

    @Column (nullable = false, length = 14)
    private String cnpj;

    @Column (nullable = false)
    private String endereco;

    @Column (nullable = false, unique = true)
    private String telefone;
}
