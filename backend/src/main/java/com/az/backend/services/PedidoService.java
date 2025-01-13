package com.az.backend.services;

import com.az.backend.dto.PedidoRequest;
import com.az.backend.dto.rabbitMQ.PedidoMessage;
import com.az.backend.exception.ResourceNotFoundException;
import com.az.backend.model.Pedido;
import com.az.backend.repositories.PedidoRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final RabbitMQSender rabbitMQSender;


    public Pedido criarPedido(PedidoRequest pedidoDto) {

        String produtosJson = new Gson().toJson(pedidoDto.getProdutos());

        Pedido pedido = Pedido.builder()
                .id(UUID.randomUUID())
                .nomeCliente(pedidoDto.getNomeCliente())
                .produtos(produtosJson)
                .status("Criado")
                .endereco(pedidoDto.getEndereco())
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido atualizarStatus(UUID id, String status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado para o ID: " + id));
        pedido.setStatus(status);
        Pedido atualizado = pedidoRepository.save(pedido);

        PedidoMessage message = PedidoMessage.builder()
                .id(pedido.getId())
                .nome(pedido.getNomeCliente())
                .status(status)
                .build();

        rabbitMQSender.sendMessage(message);
        return atualizado;
    }

}
