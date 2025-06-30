package com.example.apirest.repositories;

import com.example.apirest.entities.TalleColorProducto;
import com.example.apirest.repositories.Base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TalleColorProductoRepository extends SoftDeleteRepository<TalleColorProducto, Long> {
    // Buscar todos los talles de un color de producto
    List<TalleColorProducto> findByColorProductoId(Long colorProductoId);

    // Buscar stock de un color y talle específico
    Optional<TalleColorProducto> findByColorProductoIdAndTalleId(Long colorProductoId, Long talleId);

    // Buscar talles disponibles (stock > 0) para un color de producto
    List<TalleColorProducto> findByColorProductoIdAndStockGreaterThan(Long colorProductoId, int stock);
}