package com.az.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    private UUID id;
    private String nome;
    private double preco;
    @Column(updatable = false)
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

}
