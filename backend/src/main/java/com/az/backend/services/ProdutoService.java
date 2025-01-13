package com.az.backend.services;

import com.az.backend.model.Produto;
import com.az.backend.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private  final ProdutoRepository produtoRepository;
    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

}
