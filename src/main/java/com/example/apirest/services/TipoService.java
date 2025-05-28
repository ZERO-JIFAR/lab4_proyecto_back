package com.example.apirest.services;

import com.example.apirest.entities.Tipo;
import com.example.apirest.repositories.TipoRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoService extends BaseService<Tipo, Long> {

    public TipoService(TipoRepository tipoRepository) {
        super(tipoRepository);
    }
}