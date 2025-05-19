package com.example.apirest.services;

import com.example.apirest.entities.Direccion;
import com.example.apirest.repositories.DireccionRepository;
import org.springframework.stereotype.Service;

@Service
public class DireccionService extends BaseService<Direccion, Long> {

    public DireccionService(DireccionRepository direccionRepository) {
        super(direccionRepository);
    }
}
