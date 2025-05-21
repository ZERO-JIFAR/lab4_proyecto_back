package com.example.apirest.controllers;

import com.example.apirest.entities.TalleProducto;
import com.example.apirest.services.TalleProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talles-producto")
public class TalleProductoController {

    private final TalleProductoService talleProductoService;

    public TalleProductoController(TalleProductoService talleProductoService) {
        this.talleProductoService = talleProductoService;
    }

    @GetMapping
    public ResponseEntity<List<TalleProducto>> getAll() throws Exception {
        return ResponseEntity.ok(talleProductoService.listar());
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<TalleProducto>> porProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(talleProductoService.obtenerPorProducto(productoId));
    }

    @GetMapping("/producto/{productoId}/talle/{talleId}")
    public ResponseEntity<TalleProducto> porProductoYTalle(@PathVariable Long productoId, @PathVariable Long talleId) throws Exception {
        return ResponseEntity.ok(talleProductoService.obtenerPorProductoYTalle(productoId, talleId));
    }

    @GetMapping("/disponibles/{productoId}")
    public ResponseEntity<List<TalleProducto>> disponiblesPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(talleProductoService.obtenerDisponiblesPorProducto(productoId));
    }

}
