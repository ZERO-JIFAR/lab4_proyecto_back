package com.example.apirest.repositories;

import com.example.apirest.entities.ColorProducto;
import com.example.apirest.repositories.Base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorProductoRepository extends SoftDeleteRepository<ColorProducto, Long> {
    // Buscar todos los colores de un producto
    List<ColorProducto> findByProductoId(Long productoId);
}