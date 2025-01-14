package com.az.backend.services;

import com.az.backend.dto.PedidoRequest;
import com.az.backend.dto.ProdutoDto;
import com.az.backend.dto.rabbitMQ.PedidoMessage;
import com.az.backend.exception.ResourceNotFoundException;
import com.az.backend.model.Pedido;
import com.az.backend.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarPedido() {
        PedidoRequest pedidoRequest = mock(PedidoRequest.class);
        when(pedidoRequest.getNomeCliente()).thenReturn("Cliente Teste");
        when(pedidoRequest.getEndereco()).thenReturn("Endereço Teste");
        when(pedidoRequest.getProdutos()).thenReturn(Map.of("Produto1", new ProdutoDto()));

        Pedido pedidoSalvo = Pedido.builder()
                .id(UUID.randomUUID())
                .nomeCliente("Cliente Teste")
                .produtos("[\"Produto1\",\"Produto2\"]")
                .status("Criado")
                .endereco("Endereço Teste")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoSalvo);

        Pedido resultado = pedidoService.criarPedido(pedidoRequest);

        assertNotNull(resultado);
        assertEquals("Cliente Teste", resultado.getNomeCliente());
        assertEquals("[\"Produto1\",\"Produto2\"]", resultado.getProdutos());
        assertEquals("Criado", resultado.getStatus());

        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testListarPedidos() {
        Pedido pedido1 = Pedido.builder().id(UUID.randomUUID()).build();
        Pedido pedido2 = Pedido.builder().id(UUID.randomUUID()).build();

        when(pedidoRepository.findAll()).thenReturn(List.of(pedido1, pedido2));

        List<Pedido> pedidos = pedidoService.listarPedidos();

        assertNotNull(pedidos);
        assertEquals(2, pedidos.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void testAtualizarStatus() {
        UUID id = UUID.randomUUID();
        Pedido pedido = Pedido.builder()
                .id(id)
                .nomeCliente("Cliente Teste")
                .status("Criado")
                .build();

        when(pedidoRepository.findById(eq(id))).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.atualizarStatus(id, "Enviado");

        assertNotNull(resultado);
        assertEquals("Enviado", resultado.getStatus());

        verify(pedidoRepository, times(1)).findById(eq(id));
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(rabbitMQSender, times(1)).sendMessage(any(PedidoMessage.class));
    }

    @Test
    void testAtualizarStatusPedidoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(pedidoRepository.findById(eq(id))).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            pedidoService.atualizarStatus(id, "Enviado");
        });

        assertEquals("Pedido não encontrado para o ID: " + id, exception.getMessage());

        verify(pedidoRepository, times(1)).findById(eq(id));
        verify(pedidoRepository, never()).save(any(Pedido.class));
        verify(rabbitMQSender, never()).sendMessage(any(PedidoMessage.class));
    }

}