package com.example.apirest.services;

import com.example.apirest.entities.OrdenDeCompra;
import com.example.apirest.repositories.OrdenDeCompraRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdenDeCompraService extends BaseService<OrdenDeCompra, Long> {

    private final OrdenDeCompraRepository ordenDeCompraRepository;

    public OrdenDeCompraService(OrdenDeCompraRepository ordenDeCompraRepository) {
        super(ordenDeCompraRepository);
        this.ordenDeCompraRepository = ordenDeCompraRepository;
    }

    public List<OrdenDeCompra> obtenerPorUsuario(Long usuarioId) {
        return ordenDeCompraRepository.findByUsuarioId(usuarioId);
    }

    public List<OrdenDeCompra> obtenerPorFecha(LocalDate fecha) {
        return ordenDeCompraRepository.findByFecha(fecha);
    }

    public List<OrdenDeCompra> obtenerPorId(Long id) {

        return ordenDeCompraRepository.findById(id).map(List::of).orElse(List.of());
    }

    public OrdenDeCompra actualizarEstadoPago(Long ordenId, OrdenDeCompra.EstadoPago estadoPago) {
        OrdenDeCompra orden = ordenDeCompraRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        orden.setEstadoPago(estadoPago);
        return ordenDeCompraRepository.save(orden);
    }

    public OrdenDeCompra save(OrdenDeCompra orden) {
        return ordenDeCompraRepository.save(orden);
    }


}
