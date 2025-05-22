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
        // Puedes ajustar la lógica aquí según cómo esté estructurada tu relación con Talle.
        // Esto es solo un ejemplo suponiendo que el talle está relacionado con la categoría
        return productoRepository.findByCantidadGreaterThanAndCategoria_Id(0, talleId);
    }
}

