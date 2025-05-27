package com.example.apirest.services;

import com.example.apirest.entities.Producto;
import com.example.apirest.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService extends BaseService<Producto, Long> {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        super(productoRepository);
        this.productoRepository = productoRepository;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoria_Id(categoriaId);
    }

    public List<Producto> buscarDisponiblesPorTalle(Long talleId) {
        return productoRepository.findDisponiblesPorTalle(talleId);
    }
}

