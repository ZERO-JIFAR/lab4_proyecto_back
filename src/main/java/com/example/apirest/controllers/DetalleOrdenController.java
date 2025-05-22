package com.example.apirest.controllers;

import com.example.apirest.entities.DetalleOrden;
import com.example.apirest.entities.OrdenDeCompra;
import com.example.apirest.entities.Producto;
import com.example.apirest.services.DetalleOrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/detalles-orden")
public class DetalleOrdenController {

    private final DetalleOrdenService detalleOrdenService;

    public DetalleOrdenController(DetalleOrdenService detalleOrdenService) {
        this.detalleOrdenService = detalleOrdenService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() throws Exception {
        List<DetalleOrden> detalles = detalleOrdenService.listar();
        List<Map<String, Object>> result = detalles.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorOrden(@PathVariable Long ordenId) {
        List<DetalleOrden> detalles = detalleOrdenService.obtenerPorOrden(ordenId);
        List<Map<String, Object>> result = detalles.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // Método para convertir entidad a Map (representación lógica)
    private Map<String, Object> convertToMap(DetalleOrden detalle) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", detalle.getId());
        map.put("cantidad", detalle.getCantidad());

        // Convertir orden a representación lógica
        if (detalle.getOrden() != null) {
            Map<String, Object> ordenMap = new HashMap<>();
            OrdenDeCompra orden = detalle.getOrden();
            ordenMap.put("id", orden.getId());
            ordenMap.put("fecha", orden.getFecha());

            if (orden.getUsuario() != null) {
                Map<String, Object> usuarioMap = new HashMap<>();
                usuarioMap.put("id", orden.getUsuario().getId());
                usuarioMap.put("nombre", orden.getUsuario().getNombre());
                ordenMap.put("usuario", usuarioMap);
            }

            map.put("orden", ordenMap);
        }

        // Convertir producto a representación lógica
        if (detalle.getProducto() != null) {
            Map<String, Object> productoMap = new HashMap<>();
            Producto producto = detalle.getProducto();
            productoMap.put("id", producto.getId());
            productoMap.put("nombre", producto.getNombre());
            productoMap.put("precio", producto.getPrecio());

            if (producto.getCategoria() != null) {
                Map<String, Object> categoriaMap = new HashMap<>();
                categoriaMap.put("id", producto.getCategoria().getId());
                categoriaMap.put("nombre", producto.getCategoria().getNombre());
                productoMap.put("categoria", categoriaMap);
            }

            map.put("producto", productoMap);
        }

        return map;
    }

    // POST y PUT serían opcionales si los detalles se agregan al crear la orden
}
