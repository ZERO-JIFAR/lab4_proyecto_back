package com.example.apirest.controllers;

import com.example.apirest.entities.DetalleOrden;
import com.example.apirest.services.DetalleOrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles-orden")
public class DetalleOrdenController {

    private final DetalleOrdenService detalleOrdenService;

    public DetalleOrdenController(DetalleOrdenService detalleOrdenService) {
        this.detalleOrdenService = detalleOrdenService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleOrden>> getAll() throws Exception {
        return ResponseEntity.ok(detalleOrdenService.listar());
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<List<DetalleOrden>> obtenerPorOrden(@PathVariable Long ordenId) {
        return ResponseEntity.ok(detalleOrdenService.obtenerPorOrden(ordenId));
    }

    // POST y PUT serían opcionales si los detalles se agregan al crear la orden
}
