package com.example.apirest.services;

import com.example.apirest.entities.UsuarioDireccion;
import com.example.apirest.repositories.UsuarioDireccionRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDireccionService extends BaseService<UsuarioDireccion, Long> {

    public UsuarioDireccionService(UsuarioDireccionRepository usuarioDireccionRepository) {
        super(usuarioDireccionRepository);
    }
}