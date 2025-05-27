package com.example.apirest.repositories;

import com.example.apirest.entities.OrdenDeCompra;
import com.example.apirest.repositories.Base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdenDeCompraRepository extends SoftDeleteRepository<OrdenDeCompra, Long> {

    List<OrdenDeCompra> findByUsuarioId(Long usuarioId);

    List<OrdenDeCompra> findByFecha(LocalDate fecha);

    //List<OrdenDeCompra> encontrarPorId(Long Id);
}
