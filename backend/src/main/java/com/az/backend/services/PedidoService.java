package com.az.backend.services;

import com.az.backend.dto.PedidoRequest;
import com.az.backend.dto.rabbitMQ.PedidoMessage;
import com.az.backend.error.ComprasError;
import com.az.backend.exception.ComprasException;
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


    public Pedido criarPedido(PedidoRequest pedidoDto) throws ComprasException {

        try {

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
        }catch (Exception e){
            throw new ComprasException(e, ComprasError.CP0002);
        }
    }

    public List<Pedido> listarPedidos() throws ComprasException {
        try {
            return pedidoRepository.findAll();
        }catch (Exception e){
            throw new ComprasException(e, ComprasError.CP0003);
        }
    }

    public Pedido atualizarStatus(UUID id, String status) throws ComprasException {
        try {
            if (!List.of("Criado", "Processando", "Concluído").contains(status)) {
                throw new ComprasException(ComprasError.CP0005, "Status inválido: " + status);
            }

            Pedido pedido = pedidoRepository.findById(id)
                    .orElseThrow(() -> new ComprasException(ComprasError.CP0006, "Pedido não encontrado para o ID: " + id));


            pedido.setStatus(status);
            Pedido atualizado = pedidoRepository.save(pedido);

            PedidoMessage message = PedidoMessage.builder()
                    .id(pedido.getId())
                    .nome(pedido.getNomeCliente())
                    .status(status)
                    .build();

            try {
                rabbitMQSender.sendMessage(message);
            } catch (Exception ex) {
                throw new ComprasException(ComprasError.CP0007);
            }

            return atualizado;
        } catch (ComprasException e) {
            throw e;
        } catch (Exception e) {
            throw new ComprasException(e, ComprasError.CP0004, "Erro inesperado ao atualizar o status do pedido.");
        }
    }

}
