package com.az.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
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
public class Pedido {

    @Id
    private UUID id;
    private String nomeCliente;
    @Column(columnDefinition = "jsonb")
    private String produtos;
    private String status;
    private String endereco;
    @Column(updatable = false)
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @PreUpdate
    public void atualizarTimestamp() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
