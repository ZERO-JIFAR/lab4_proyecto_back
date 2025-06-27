// OrdenDeCompraRepository.java
package com.example.apirest.repositories;

import com.example.apirest.entities.OrdenDeCompra;
import java.time.LocalDate;
import java.util.List;

public interface OrdenDeCompraRepository extends BaseRepository<OrdenDeCompra, Long> {
    List<OrdenDeCompra> findByUsuarioId(Long usuarioId);
    List<OrdenDeCompra> findByFecha(LocalDate fecha);
}