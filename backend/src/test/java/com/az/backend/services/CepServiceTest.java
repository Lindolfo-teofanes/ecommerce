package com.az.backend.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CepServiceTest {

    @InjectMocks
    private CepService cepService;

    @Mock
    private RestTemplate restTemplate;

    public CepServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarEnderecoParaCepValido() {
        String cep = "01001000";
        String respostaMock = "{"
                + "\"logradouro\":\"Praça Ary Coelho\","
                + "\"bairro\":\"Centro\","
                + "\"localidade\":\"Campo Grande\","
                + "\"uf\":\"MS\""
                + "}";

        when(restTemplate.getForObject(String.format("https://viacep.com.br/ws/%s/json/", cep), String.class))
                .thenReturn(respostaMock);

        String endereco = cepService.buscarEnderecoPorCep(cep);
        assertEquals("Praça da Sé, Sé, São Paulo-SP, 01001000", endereco);
    }



}