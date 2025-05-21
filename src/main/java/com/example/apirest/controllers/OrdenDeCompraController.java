package com.example.apirest.controllers;

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

    @GetMapping
    public ResponseEntity<List<OrdenDeCompra>> getAll() throws Exception {
        return ResponseEntity.ok(ordenService.listar());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<OrdenDeCompra>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ordenService.obtenerPorUsuario(usuarioId));
    }

    @GetMapping("/{fecha}")
    public ResponseEntity<List<OrdenDeCompra>> obtenerPorFecha(@PathVariable LocalDate fecha) {
        return ResponseEntity.ok(ordenService.obtenerPorFecha(fecha));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrdenDeCompra>> obtenerPorId(@PathVariable Long Id){
        return ResponseEntity.ok(ordenService.obtenerPorId(Id));
    }
}
