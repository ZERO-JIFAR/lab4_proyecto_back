package com.example.apirest.repositories;

import com.example.apirest.entities.DetalleOrden;
import com.example.apirest.repositories.Base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleOrdenRepository extends SoftDeleteRepository<DetalleOrden, Long> {
    List<DetalleOrden> findByOrdenDeCompraId(Long ordenId);
}
