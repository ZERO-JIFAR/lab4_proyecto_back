package com.example.apirest.repositories;

import com.example.apirest.entities.Direccion;
import com.example.apirest.repositories.Base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends SoftDeleteRepository<Direccion, Long> {
}
