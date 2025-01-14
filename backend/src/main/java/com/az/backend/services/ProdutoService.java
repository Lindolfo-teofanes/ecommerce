package com.az.backend.services;

import com.az.backend.error.ComprasError;
import com.az.backend.exception.ComprasException;
import com.az.backend.model.Produto;
import com.az.backend.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private  final ProdutoRepository produtoRepository;
    public List<Produto> buscarTodos() throws ComprasException {
        try {
            return produtoRepository.findAll();
        }catch (Exception e){
            throw new ComprasException(e, ComprasError.CP0008);
        }
    }

}
