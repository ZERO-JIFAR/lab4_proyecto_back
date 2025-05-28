package com.example.apirest.repositories;

import com.example.apirest.entities.UsuarioDireccion;
import com.example.apirest.repositories.Base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDireccionRepository extends SoftDeleteRepository<UsuarioDireccion, Long> {
}