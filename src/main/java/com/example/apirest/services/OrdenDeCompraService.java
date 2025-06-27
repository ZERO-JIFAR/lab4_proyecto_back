package com.example.apirest.services;

import com.example.apirest.dto.OrdenDeCompraDTO;
import com.example.apirest.dto.OrdenDeCompraResponseDTO;
import com.example.apirest.entities.DetalleOrden;
import com.example.apirest.entities.OrdenDeCompra;
import com.example.apirest.entities.Producto;
import com.example.apirest.entities.Usuario;
import com.example.apirest.repositories.OrdenDeCompraRepository;
import com.example.apirest.repositories.ProductoRepository;
import com.example.apirest.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenDeCompraService extends BaseService<OrdenDeCompra, Long> {

    private final OrdenDeCompraRepository ordenDeCompraRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public OrdenDeCompraService(OrdenDeCompraRepository ordenDeCompraRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        super(ordenDeCompraRepository);
        this.ordenDeCompraRepository = ordenDeCompraRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public OrdenDeCompra crearOrdenDeCompraDesdeDTO(OrdenDeCompraDTO dto) throws Exception {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        List<DetalleOrden> detalles = new ArrayList<>();
        OrdenDeCompra orden = new OrdenDeCompra();
        orden.setUsuario(usuario);
        orden.setFecha(LocalDate.now());
        orden.setEstadoPago(OrdenDeCompra.EstadoPago.PENDIENTE);

        for (OrdenDeCompraDTO.ItemOrdenDTO item : dto.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new Exception("Producto no encontrado: " + item.getProductoId()));

            DetalleOrden detalle = new DetalleOrden();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(BigDecimal.valueOf(item.getPrecioUnitario()));
            detalle.setTalle(item.getTalle());
            detalle.setColor(item.getColor());
            detalle.setOrden(orden);
            detalles.add(detalle);
        }

        orden.setDetalle(detalles);

        return ordenDeCompraRepository.save(orden);
    }

    // NUEVO: Mapear OrdenDeCompra a DTO plano
    public OrdenDeCompraResponseDTO mapToResponseDTO(OrdenDeCompra orden) {
        OrdenDeCompraResponseDTO dto = new OrdenDeCompraResponseDTO();
        dto.setId(orden.getId());
        dto.setFecha(orden.getFecha());
        dto.setEstadoPago(orden.getEstadoPago().toString());

        List<OrdenDeCompraResponseDTO.DetalleOrdenDTO> detalles = new ArrayList<>();
        for (DetalleOrden det : orden.getDetalle()) {
            OrdenDeCompraResponseDTO.DetalleOrdenDTO detDto = new OrdenDeCompraResponseDTO.DetalleOrdenDTO();

            OrdenDeCompraResponseDTO.ProductoDTO productoDto = new OrdenDeCompraResponseDTO.ProductoDTO();
            if (det.getProducto() != null) {
                productoDto.setNombre(det.getProducto().getNombre());
                productoDto.setImagenUrl(det.getProducto().getImagenUrl());
                productoDto.setColor(det.getProducto().getColor());
            } else {
                productoDto.setNombre("Producto desconocido");
                productoDto.setImagenUrl(null);
                productoDto.setColor(null);
            }
            detDto.setProducto(productoDto);
            detDto.setCantidad(det.getCantidad());
            detDto.setPrecioUnitario(det.getPrecioUnitario());
            detalles.add(detDto);
        }
        dto.setDetalle(detalles);
        return dto;
    }

    public List<OrdenDeCompraResponseDTO> obtenerPorUsuarioDTO(Long usuarioId) {
        List<OrdenDeCompra> ordenes = ordenDeCompraRepository.findByUsuarioId(usuarioId);
        List<OrdenDeCompraResponseDTO> dtos = new ArrayList<>();
        for (OrdenDeCompra orden : ordenes) {
            dtos.add(mapToResponseDTO(orden));
        }
        return dtos;
    }

    public List<OrdenDeCompraResponseDTO> obtenerPorFechaDTO(LocalDate fecha) {
        List<OrdenDeCompra> ordenes = ordenDeCompraRepository.findByFecha(fecha);
        List<OrdenDeCompraResponseDTO> dtos = new ArrayList<>();
        for (OrdenDeCompra orden : ordenes) {
            dtos.add(mapToResponseDTO(orden));
        }
        return dtos;
    }

    public Optional<OrdenDeCompraResponseDTO> obtenerPorIdDTO(Long id) {
        return ordenDeCompraRepository.findById(id).map(this::mapToResponseDTO);

    }

    public Optional<OrdenDeCompra> findById(Long id) {
        return ordenDeCompraRepository.findById(id);
    }
}