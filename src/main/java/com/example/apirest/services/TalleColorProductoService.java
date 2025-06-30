package com.example.apirest.services;

import com.example.apirest.entities.TalleColorProducto;
import com.example.apirest.repositories.TalleColorProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TalleColorProductoService extends BaseService<TalleColorProducto, Long> {

    private final TalleColorProductoRepository talleColorProductoRepository;

    public TalleColorProductoService(TalleColorProductoRepository talleColorProductoRepository) {
        super(talleColorProductoRepository);
        this.talleColorProductoRepository = talleColorProductoRepository;
    }

    public List<TalleColorProducto> obtenerPorColorProducto(Long colorProductoId) {
        return talleColorProductoRepository.findByColorProductoId(colorProductoId);
    }

    public Optional<TalleColorProducto> obtenerPorColorYtalle(Long colorProductoId, Long talleId) {
        return talleColorProductoRepository.findByColorProductoIdAndTalleId(colorProductoId, talleId);
    }

    public List<TalleColorProducto> obtenerDisponiblesPorColorProducto(Long colorProductoId) {
        return talleColorProductoRepository.findByColorProductoIdAndStockGreaterThan(colorProductoId, 0);
    }
}
