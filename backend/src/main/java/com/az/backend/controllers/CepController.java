package com.az.backend.controllers;

import com.az.backend.services.CepService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cep")
@CrossOrigin(origins = "http://localhost:5173")
public class CepController {

    private final CepService cepService;

    public CepController(CepService cepService) {
        this.cepService = cepService;
    }

    @GetMapping("/{cep}")
    public String buscarCep(@PathVariable String cep) {
        return cepService.buscarEnderecoPorCep(cep);
    }

}
