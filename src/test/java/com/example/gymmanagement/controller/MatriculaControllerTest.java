package com.example.demo.controller;

import com.example.demo.dto.Request.MatriculaRequestDto;
import com.example.demo.model.Aluno;
import com.example.demo.model.Matricula;
import com.example.demo.model.Plano;
import com.example.demo.service.MatriculaService;
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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatriculaController.class)
@AutoConfigureMockMvc(addFilters = false)
class MatriculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockBean
    private MatriculaService matriculaService;

    @Test
    @WithMockUser
    void criarMatricula_retornaCreated() throws Exception {
        MatriculaRequestDto request = new MatriculaRequestDto(
                1L,
                Plano.BASIC,
                LocalDate.of(2026, 4, 27),
                null,
                true
        );

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Joao");

        Matricula matricula = new Matricula();
        matricula.setId(5L);
        matricula.setAluno(aluno);
        matricula.setPlano(Plano.BASIC);
        matricula.setDataInicio(LocalDate.of(2026, 4, 27));
        matricula.setDataFim(null);
        matricula.setAtiva(true);

        when(matriculaService.criarMatricula(any())).thenReturn(matricula);

        mockMvc.perform(post("/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.alunoId").value(1));
    }

    @Test
    @WithMockUser
    void criarMatricula_invalida_retornaBadRequest() throws Exception {
        MatriculaRequestDto request = new MatriculaRequestDto(
                null,
                null,
                null,
                null,
                null
        );

        mockMvc.perform(post("/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void buscarMatricula_retornaOk() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Maria");

        Matricula matricula = new Matricula();
        matricula.setId(11L);
        matricula.setAluno(aluno);
        matricula.setPlano(Plano.PREMIUM);
        matricula.setDataInicio(LocalDate.of(2026, 4, 1));
        matricula.setDataFim(LocalDate.of(2026, 6, 1));
        matricula.setAtiva(true);

        when(matriculaService.buscarMatricula(11L)).thenReturn(matricula);

        mockMvc.perform(get("/matriculas/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11))
                .andExpect(jsonPath("$.alunoId").value(2));
    }
}
