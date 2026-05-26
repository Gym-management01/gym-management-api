package com.example.demo.controller;

import com.example.demo.dto.Request.AuthRequestDto;
import com.example.demo.dto.Response.AuthResponseDto;
import com.example.demo.dto.Response.FuncionarioResponseDto;
import com.example.demo.model.Funcionario;
import com.example.demo.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.FuncionarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, FuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            Funcionario funcionario = (Funcionario) authentication.getPrincipal();
            String token = jwtService.generateToken(funcionario);
            return ResponseEntity.ok(new AuthResponseDto(token, "Bearer"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<FuncionarioResponseDto> me(Authentication authentication) {
        Funcionario funcionario = (Funcionario) authentication.getPrincipal();
        return ResponseEntity.ok(new FuncionarioResponseDto(funcionario));
    }

    @GetMapping("/debug/{username}")
    public ResponseEntity<?> debug(@PathVariable String username) {
        Optional<Funcionario> f = funcionarioRepository.findByUsername(username);
        if (f.isEmpty()) return ResponseEntity.ok("User not found");
        String hash = f.get().getPassword();
        boolean matches = passwordEncoder.matches("123", hash);
        return ResponseEntity.ok("Hash: " + hash + " | Length: " + (hash != null ? hash.length() : 0) + " | Matches '123': " + matches);
    }
}

