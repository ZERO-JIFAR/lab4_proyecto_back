package com.example.apirest.services;

import com.example.apirest.entities.ColorProducto;
import com.example.apirest.repositories.ColorProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorProductoService extends BaseService<ColorProducto, Long> {

    private final ColorProductoRepository colorProductoRepository;

    public ColorProductoService(ColorProductoRepository colorProductoRepository) {
        super(colorProductoRepository);
        this.colorProductoRepository = colorProductoRepository;
    }

    public List<ColorProducto> obtenerPorProducto(Long productoId) {
        return colorProductoRepository.findByProductoId(productoId);
    }

    public Optional<ColorProducto> buscarPorId(Long id) throws Exception {
        return colorProductoRepository.findById(id);
    }
}
