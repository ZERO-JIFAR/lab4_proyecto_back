package com.example.apirest.controllers;

import com.example.apirest.entities.ColorProducto;
import com.example.apirest.services.ColorProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colores-producto")
public class ColorProductoController {

    private final ColorProductoService colorProductoService;

    public ColorProductoController(ColorProductoService colorProductoService) {
        this.colorProductoService = colorProductoService;
    }

    @GetMapping
    public ResponseEntity<List<ColorProducto>> getAll() throws Exception {
        return ResponseEntity.ok(colorProductoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorProducto> getById(@PathVariable Long id) throws Exception {
        return colorProductoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ColorProducto>> getByProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(colorProductoService.obtenerPorProducto(productoId));
    }

    @PostMapping
    public ResponseEntity<ColorProducto> create(@RequestBody ColorProducto colorProducto) throws Exception {
        return ResponseEntity.ok(colorProductoService.crear(colorProducto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColorProducto> update(@PathVariable Long id, @RequestBody ColorProducto colorProducto) throws Exception {
        colorProducto.setId(id);
        return ResponseEntity.ok(colorProductoService.actualizar(colorProducto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        colorProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}