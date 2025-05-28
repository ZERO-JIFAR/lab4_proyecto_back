package com.example.apirest.repositories;

import com.example.apirest.entities.Tipo;
import com.example.apirest.repositories.Base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRepository extends SoftDeleteRepository<Tipo, Long> {
}