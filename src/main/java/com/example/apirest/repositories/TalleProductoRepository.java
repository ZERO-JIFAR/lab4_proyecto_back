package com.example.apirest.repositories;

import com.example.apirest.entities.TalleProducto;
import com.example.apirest.repositories.Base.SoftDeleteRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TalleProductoRepository extends SoftDeleteRepository<TalleProducto, Long> {
    List<TalleProducto> findByProductoId(Long productoId);

    Optional<TalleProducto> findByProductoIdAndTalleId(Long productoId, Long talleId);

    @Query("SELECT tp FROM TalleProducto tp WHERE tp.producto.id = :productoId AND tp.stock > 0")
    List<TalleProducto> findDisponiblesPorProducto(@Param("productoId") Long productoId);
}
