package com.example.apirest.repositories;

import com.example.apirest.entities.Categoria;
import com.example.apirest.repositories.Base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends SoftDeleteRepository<Categoria, Long> {
}
