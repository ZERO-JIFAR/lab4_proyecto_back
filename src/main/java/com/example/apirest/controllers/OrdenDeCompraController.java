package com.example.apirest.controllers;

import com.example.apirest.dto.OrdenDeCompraDTO;
import com.example.apirest.dto.OrdenDeCompraResponseDTO;
import com.example.apirest.entities.OrdenDeCompra;
import com.example.apirest.services.OrdenDeCompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ordenes")
public class OrdenDeCompraController {

    private final OrdenDeCompraService ordenService;

    public OrdenDeCompraController(OrdenDeCompraService ordenService) {
        this.ordenService = ordenService;
    }

    // Si necesitas un endpoint para listar todas las órdenes (admin)
    @GetMapping
    public ResponseEntity<List<OrdenDeCompraResponseDTO>> getAll() throws Exception {
        // Si tienes un método para listar todas, deberías mapearlas a DTO también
        throw new UnsupportedOperationException("No implementado");
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<OrdenDeCompraResponseDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ordenService.obtenerPorUsuarioDTO(usuarioId));
    }

    @GetMapping("/{fecha}")
    public ResponseEntity<List<OrdenDeCompraResponseDTO>> obtenerPorFecha(@PathVariable LocalDate fecha) {
        return ResponseEntity.ok(ordenService.obtenerPorFechaDTO(fecha));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrdenDeCompraResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ordenService.obtenerPorIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrdenDeCompraResponseDTO> crear(@RequestBody OrdenDeCompraDTO ordenDTO) throws Exception {
        OrdenDeCompra orden = ordenService.crearOrdenDeCompraDesdeDTO(ordenDTO);
        OrdenDeCompraResponseDTO responseDTO = ordenService.mapToResponseDTO(orden);
        return ResponseEntity.ok(responseDTO);
    }
}