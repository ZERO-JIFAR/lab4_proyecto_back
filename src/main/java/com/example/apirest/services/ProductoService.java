package com.example.apirest.services;

import com.example.apirest.entities.Producto;
import com.example.apirest.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService extends BaseService<Producto, Long> {

    public ProductoService(ProductoRepository productoRepository) {
        super(productoRepository);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return ((ProductoRepository) baseRepository).findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return ((ProductoRepository) baseRepository).findByCategoria_Id(categoriaId);
    }

    public List<Producto> buscarDisponiblesPorTalle(Long talleId) {
        return ((ProductoRepository) baseRepository).findDisponiblesPorTalle(talleId);
    }
}
