package com.example.demo.controller;

import com.example.demo.dto.Request.FuncionarioRequestDto;
import com.example.demo.model.Funcionario;
import com.example.demo.service.FuncionarioService;
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

@WebMvcTest(FuncionarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockBean
    private FuncionarioService funcionarioService;

    @Test
    @WithMockUser
    void criarFuncionario_retornaCreated() throws Exception {
        FuncionarioRequestDto request = new FuncionarioRequestDto(
                "Carlos",
                "INSTRUTOR",
                "11999998888",
                "12345678901",
                new BigDecimal("2500.00")
        );

        Funcionario funcionario = new Funcionario();
        funcionario.setId(3L);
        funcionario.setNome("Carlos");
        funcionario.setCargo("Instrutor");
        funcionario.setTelefone("11999998888");
        funcionario.setCpf("12345678901");
        funcionario.setSalario(new BigDecimal("2500.00"));

        when(funcionarioService.criarFuncionario(any())).thenReturn(funcionario);

        mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nome").value("Carlos"));
    }

    @Test
    @WithMockUser
    void criarFuncionario_invalido_retornaBadRequest() throws Exception {
        FuncionarioRequestDto request = new FuncionarioRequestDto(
                "",
                "",
                "",
                "",
                null
        );

        mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void buscarFuncionario_retornaOk() throws Exception {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(9L);
        funcionario.setNome("Ana");
        funcionario.setCargo("Recepcao");
        funcionario.setTelefone("1188887777");
        funcionario.setCpf("98765432100");
        funcionario.setSalario(new BigDecimal("1800.00"));

        when(funcionarioService.buscarFuncionario(9L)).thenReturn(funcionario);

        mockMvc.perform(get("/funcionarios/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(9))
                .andExpect(jsonPath("$.nome").value("Ana"));
    }
}
