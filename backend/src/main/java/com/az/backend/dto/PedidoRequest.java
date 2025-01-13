package com.az.backend.dto;


import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

@Builder
@Data
public class PedidoRequest {

    @NotBlank(message = "O nome do cliente é obrigatório.")
    private String nomeCliente;

    @NotBlank(message = "O Endereço é obrigatório.")
    private String endereco;

    @NotNull(message = "Os produtos são obrigatórios.")
    private Map<String, ProdutoDto> produtos;


}
