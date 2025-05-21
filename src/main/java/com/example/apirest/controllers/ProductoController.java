package com.example.apirest.controllers;

import com.example.apirest.entities.Producto;
import com.example.apirest.services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> getAll() throws Exception {
        return ResponseEntity.ok(productoService.listar());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(id));
    }

    @GetMapping("/disponibles/talle/{id}")
    public ResponseEntity<List<Producto>> buscarDisponiblesPorTalle(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarDisponiblesPorTalle(id));
    }
}
