package com.example.apirest.repositories;

import com.example.apirest.entities.DetalleOrden;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleOrdenRepository extends BaseRepository<DetalleOrden, Long> {
    List<DetalleOrden> findByOrden_Id(Long ordenId);
}
