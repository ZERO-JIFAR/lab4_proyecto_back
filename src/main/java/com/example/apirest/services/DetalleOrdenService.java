package com.example.apirest.services;

import com.example.apirest.entities.DetalleOrden;
import com.example.apirest.repositories.DetalleOrdenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleOrdenService extends BaseService<DetalleOrden, Long> {

    private final DetalleOrdenRepository detalleOrdenRepository;

    public DetalleOrdenService(DetalleOrdenRepository detalleOrdenRepository) {
        super(detalleOrdenRepository);
        this.detalleOrdenRepository = detalleOrdenRepository;
    }

    public List<DetalleOrden> obtenerPorOrden(Long ordenId) {
        return detalleOrdenRepository.findByOrdenId(ordenId);
    }
}
