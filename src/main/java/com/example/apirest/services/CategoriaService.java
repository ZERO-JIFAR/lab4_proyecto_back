package com.example.apirest.services;

import com.example.apirest.entities.Categoria;
import com.example.apirest.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService extends BaseService<Categoria, Long> {

    public CategoriaService(CategoriaRepository categoriaRepository) {
        super(categoriaRepository);
    }
}
