package com.example.apirest.services;

import com.example.apirest.entities.TipoTalle;
import com.example.apirest.repositories.TipoTalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoTalleService {

    @Autowired
    private TipoTalleRepository tipoTalleRepository;

    public List<TipoTalle> findAll() {
        return tipoTalleRepository.findAll();
    }

    public Optional<TipoTalle> findById(Long id) {
        return tipoTalleRepository.findById(id);
    }

    public TipoTalle save(TipoTalle tipoTalle) {
        return tipoTalleRepository.save(tipoTalle);
    }

    public void deleteById(Long id) {
        tipoTalleRepository.deleteById(id);
    }
}