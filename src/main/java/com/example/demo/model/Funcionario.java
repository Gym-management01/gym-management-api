package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "funcionarios")
public class Funcionario implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column (nullable = false, length = 100)
        private String nome;

        @Column (nullable = false, length = 11)
        private String cpf;

        @Enumerated(EnumType.STRING)
        @Column (nullable = false)
        private UserRole cargo;

        @Column (nullable = false, unique = true)
        private String telefone;

        @Column (nullable = false)
        private BigDecimal salario;

        @Column(nullable = false, unique = true, length = 100)
        private String username;

        @Column(nullable = false)
        private String password;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(cargo.name()));
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}
