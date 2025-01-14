package com.az.backend.services;

import com.az.backend.error.ComprasError;
import com.az.backend.exception.ComprasException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Service
public class CepService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/%s/json/";

    public String buscarEnderecoPorCep(String cep) throws ComprasException {

        try{
            RestTemplate restTemplate = new RestTemplate();

            String url = String.format(VIACEP_URL, cep);
            String response = restTemplate.getForObject(url, String.class);

            JSONObject json = new JSONObject(response);

            String logradouro = json.getString("logradouro");
            String bairro = json.getString("bairro");
            String localidade = json.getString("localidade");
            String uf = json.getString("uf");

            return String.format("%s, %s, %s-%s, %s", logradouro, bairro, localidade, uf, cep);
        }catch (HttpClientErrorException e) {
            throw new ComprasException(ComprasError.CP0001);
        } catch (ResourceAccessException e) {
            throw new ComprasException(e, ComprasError.CP9999);
        }

    }
}
