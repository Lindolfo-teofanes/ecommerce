package com.az.backend.dto.rabbitMQ;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PedidoMessage {
    private UUID id;
    private String nome;
    private String status;

}
