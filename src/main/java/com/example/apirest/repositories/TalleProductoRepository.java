package com.example.apirest.repositories;

import com.example.apirest.entities.TalleProducto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TalleProductoRepository extends BaseRepository<TalleProducto, Long> {
    List<TalleProducto> findByProducto_Id(Long productoId);

    Optional<TalleProducto> findByProducto_IdAndTalle_Id(Long productoId, Long talleId);

    List<TalleProducto> findByProducto_IdAndProducto_ActivoTrue(Long productoId);
}
