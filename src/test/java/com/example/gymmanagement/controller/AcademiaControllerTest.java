package com.example.demo.controller;

import com.example.demo.dto.Request.AcademiaRequestDto;
import com.example.demo.model.Academia;
import com.example.demo.service.AcademiaService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AcademiaController.class)
@AutoConfigureMockMvc(addFilters = false)
class AcademiaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockBean
    private AcademiaService academiaService;

    @Test
    @WithMockUser
    void criarAcademia_retornaCreated() throws Exception {
        AcademiaRequestDto request = new AcademiaRequestDto(
                "Academia X",
                "Rua A, 123",
                "1133334444",
                "12345678000199"
        );

        Academia academia = new Academia();
        academia.setId(1L);
        academia.setNome("Academia X");
        academia.setEndereco("Rua A, 123");
        academia.setTelefone("1133334444");
        academia.setCnpj("12345678000199");

        when(academiaService.criarAcademia(any())).thenReturn(academia);

        mockMvc.perform(post("/academias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Academia X"));
    }

    @Test
    @WithMockUser
    void criarAcademia_invalida_retornaBadRequest() throws Exception {
        AcademiaRequestDto request = new AcademiaRequestDto(
                "",
                "",
                "",
                ""
        );

        mockMvc.perform(post("/academias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void buscarAcademia_retornaOk() throws Exception {
        Academia academia = new Academia();
        academia.setId(7L);
        academia.setNome("Academia Y");
        academia.setEndereco("Rua B, 456");
        academia.setTelefone("1144445555");
        academia.setCnpj("00987654000100");

        when(academiaService.buscarAcademia(7L)).thenReturn(academia);

        mockMvc.perform(get("/academias/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.nome").value("Academia Y"));
    }
}
