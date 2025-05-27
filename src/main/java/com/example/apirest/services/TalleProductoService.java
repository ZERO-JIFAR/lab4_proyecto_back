package com.example.apirest.services;

import com.example.apirest.entities.TalleProducto;
import com.example.apirest.repositories.TalleProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalleProductoService extends BaseService<TalleProducto, Long> {

    private final TalleProductoRepository talleProductoRepository;

    public TalleProductoService(TalleProductoRepository talleProductoRepository) {
        super(talleProductoRepository);
        this.talleProductoRepository = talleProductoRepository;
    }

    public List<TalleProducto> obtenerPorProducto(Long productoId) {
        return talleProductoRepository.findByProductoId(productoId);
    }

    public TalleProducto obtenerPorProductoYTalle(Long productoId, Long talleId) throws Exception {
        return talleProductoRepository.findByProductoIdAndTalleId(productoId, talleId)
                .orElseThrow(() -> new Exception("TalleProducto no encontrado para productoId=" + productoId + " y talleId=" + talleId));
    }

    public List<TalleProducto> obtenerDisponiblesPorProducto(Long productoId) {
        return talleProductoRepository.findDisponiblesPorProducto(productoId);
    }
}
