package com.az.backend.controllers;

import com.az.backend.dto.PedidoRequest;
import com.az.backend.model.Pedido;
import com.az.backend.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {


    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }


    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoRequest pedido) {
        return ResponseEntity.ok(pedidoService.criarPedido(pedido));
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable UUID id, @RequestParam String status) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, status));
    }

}
