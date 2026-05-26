package com.example.demo.controller;

import com.example.demo.dto.Request.PagamentoPagamentoDto;
import com.example.demo.dto.Request.PagamentoRequestDto;
import com.example.demo.dto.Response.PagamentoResponseDto;
import com.example.demo.model.PagamentoStatus;
import com.example.demo.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDto> criarPagamento(@RequestBody @Valid PagamentoRequestDto request) {
        PagamentoResponseDto response = pagamentoService.criarPagamento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDto> buscarPagamento(@PathVariable Long id) {
        return ResponseEntity.ok(pagamentoService.buscarPagamento(id));
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponseDto>> listarPagamentos(
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) Long matriculaId,
            @RequestParam(required = false) PagamentoStatus status) {
        List<PagamentoResponseDto> resposta = pagamentoService.listarPagamentos(alunoId, matriculaId, status);
        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<PagamentoResponseDto> pagar(@PathVariable Long id,
                                                      @RequestBody(required = false) PagamentoPagamentoDto request) {
        PagamentoResponseDto response = pagamentoService.pagar(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/atrasados")
    public ResponseEntity<List<PagamentoResponseDto>> atualizarAtrasados() {
        List<PagamentoResponseDto> resposta = pagamentoService.atualizarAtrasados();
        return ResponseEntity.ok(resposta);
    }
}
