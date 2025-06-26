package com.example.apirest.services;

import com.example.apirest.entities.DetalleOrden;
import com.example.apirest.entities.OrdenDeCompra;
import com.example.apirest.entities.Producto;
import com.example.apirest.repositories.OrdenDeCompraRepository;
import com.example.apirest.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenDeCompraService extends BaseService<OrdenDeCompra, Long> {

    private final OrdenDeCompraRepository ordenDeCompraRepository;
    private final ProductoRepository productoRepository;

    public OrdenDeCompraService(OrdenDeCompraRepository ordenDeCompraRepository, ProductoRepository productoRepository) {
        super(ordenDeCompraRepository);
        this.ordenDeCompraRepository = ordenDeCompraRepository;
        this.productoRepository = productoRepository;
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

    @Transactional
    public OrdenDeCompra generarOrdenCompra(List<Long> productIds) throws Exception {
        List<DetalleOrden> detalles = new ArrayList<>();

        for (Long id : productIds) {
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new Exception("No se encontró el producto con ID: " + id));

            DetalleOrden detalle = new DetalleOrden();
            detalle.setProducto(producto);
            detalle.setCantidad(1); // Por defecto 1, puedes ajustar según necesites
            detalle.setPrecioUnitario(BigDecimal.valueOf(producto.getPrecio()));
            detalles.add(detalle);
        }

        OrdenDeCompra ordenDeCompra = new OrdenDeCompra();
        ordenDeCompra.setFecha(LocalDate.now());
        ordenDeCompra.setEstadoPago(OrdenDeCompra.EstadoPago.PENDIENTE);
        ordenDeCompra.setDetalle(detalles);

        // Guardar la orden
        OrdenDeCompra ordenGuardada = ordenDeCompraRepository.save(ordenDeCompra);

        // Establecer la relación bidireccional
        for (DetalleOrden detalle : detalles) {
            detalle.setOrden(ordenGuardada);
        }

        return ordenGuardada;
    }
}