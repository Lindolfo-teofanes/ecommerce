package com.az.backend.services;

import com.az.backend.dto.rabbitMQ.PedidoMessage;
import com.az.backend.model.Pedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

@Service
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    public RabbitMQSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(PedidoMessage pedido) {
        try {
            String message = objectMapper.writeValueAsString(pedido);
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            System.out.println("Mensagem enviada ao RabbitMQ: " + message);
        }catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar o objeto Pedido", e);
        }


    }

}
