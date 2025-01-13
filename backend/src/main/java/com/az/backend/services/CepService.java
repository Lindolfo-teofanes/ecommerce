package com.az.backend.services;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CepService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/%s/json/";

    public String buscarEnderecoPorCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format(VIACEP_URL, cep);
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        if (json.has("erro")) {
            throw new IllegalArgumentException("CEP inválido: " + cep);
        }

        String logradouro = json.getString("logradouro");
        String bairro = json.getString("bairro");
        String localidade = json.getString("localidade");
        String uf = json.getString("uf");

        return String.format("%s, %s, %s-%s, %s", logradouro, bairro, localidade, uf, cep);
    }
}
