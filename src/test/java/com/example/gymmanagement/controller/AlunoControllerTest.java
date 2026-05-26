package com.example.demo.controller;

import com.example.demo.dto.Request.AlunoRequestDto;
import com.example.demo.dto.Response.AlunoResponseDto;
import com.example.demo.model.Plano;
import com.example.demo.service.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlunoController.class)
@AutoConfigureMockMvc(addFilters = false)
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockBean
    private AlunoService alunoService;

    @Test
    @WithMockUser
    void cadastrarAluno_retornaCreated() throws Exception {
        AlunoRequestDto request = new AlunoRequestDto(
                "Joao",
                "11999999999",
                "12345678901",
                Plano.BASIC,
                new BigDecimal("100.00")
        );

        AlunoResponseDto response = new AlunoResponseDto(
                1L,
                "Joao",
                "11999999999",
                new BigDecimal("100.00"),
                Plano.BASIC,
                "12345678901"
        );

        when(alunoService.cadastrarAluno(any())).thenReturn(response);

        mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser
    void cadastrarAluno_invalido_retornaBadRequest() throws Exception {
        AlunoRequestDto request = new AlunoRequestDto(
                "",
                "",
                "",
                null,
                null
        );

        mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void buscarAluno_retornaOk() throws Exception {
        AlunoResponseDto response = new AlunoResponseDto(
                10L,
                "Maria",
                "11988887777",
                new BigDecimal("150.00"),
                Plano.PREMIUM,
                "98765432100"
        );

        when(alunoService.buscarAluno(10L)).thenReturn(response);

        mockMvc.perform(get("/alunos/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.nome").value("Maria"));
    }
}
