package com.az.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdutoDto {

    @NotBlank(message = "O nome do produto é obrigatório.")
    private String nome;

    @NotNull(message = "A quantidade do produto é obrigatória.")
    private Integer quantidade;

    @NotNull(message = "O preço do produto é obrigatório.")
    private Double preco;
}
