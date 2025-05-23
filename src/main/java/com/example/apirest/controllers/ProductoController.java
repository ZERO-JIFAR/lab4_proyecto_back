package com.example.apirest.controllers;

import com.example.apirest.entities.Categoria;
import com.example.apirest.entities.Producto;
import com.example.apirest.services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() throws Exception {
        List<Producto> productos = productoService.listar();
        List<Map<String, Object>> result = productos.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Map<String, Object>>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        List<Map<String, Object>> result = productos.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<Map<String, Object>>> buscarPorCategoria(@PathVariable Long id) {
        List<Producto> productos = productoService.buscarPorCategoria(id);
        List<Map<String, Object>> result = productos.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/disponibles/talle/{id}")
    public ResponseEntity<List<Map<String, Object>>> buscarDisponiblesPorTalle(@PathVariable Long id) {
        List<Producto> productos = productoService.buscarDisponiblesPorTalle(id);
        List<Map<String, Object>> result = productos.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    //  convertir entidad a Map (representación lógica)
    private Map<String, Object> convertToMap(Producto producto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", producto.getId());
        map.put("nombre", producto.getNombre());
        map.put("cantidad", producto.getCantidad());
        map.put("precio", producto.getPrecio());
        map.put("descripcion", producto.getDescripcion());
        map.put("color", producto.getColor());
        map.put("marca", producto.getMarca());
        map.put("activo", producto.isActivo());

        // Convertir categoría a representación lógica
        if (producto.getCategoria() != null) {
            Map<String, Object> categoriaMap = new HashMap<>();
            Categoria categoria = producto.getCategoria();
            categoriaMap.put("id", categoria.getId());
            categoriaMap.put("nombre", categoria.getNombre());
            categoriaMap.put("descripcion", categoria.getDescripcion());
            map.put("categoria", categoriaMap);
        }

        return map;
    }
}
