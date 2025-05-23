package com.example.apirest.controllers;

import com.example.apirest.entities.DetalleOrden;
import com.example.apirest.entities.OrdenDeCompra;
import com.example.apirest.entities.Usuario;
import com.example.apirest.services.OrdenDeCompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordenes")
public class OrdenDeCompraController {

    private final OrdenDeCompraService ordenService;

    public OrdenDeCompraController(OrdenDeCompraService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() throws Exception {
        List<OrdenDeCompra> ordenes = ordenService.listar();
        List<Map<String, Object>> result = ordenes.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<OrdenDeCompra> ordenes = ordenService.obtenerPorUsuario(usuarioId);
        List<Map<String, Object>> result = ordenes.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorFecha(@PathVariable LocalDate fecha) {
        List<OrdenDeCompra> ordenes = ordenService.obtenerPorFecha(fecha);
        List<Map<String, Object>> result = ordenes.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorId(@PathVariable Long id) {
        List<OrdenDeCompra> ordenes = ordenService.obtenerPorId(id);
        List<Map<String, Object>> result = ordenes.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // entidad a Map (representación lógica)
    private Map<String, Object> convertToMap(OrdenDeCompra orden) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", orden.getId());
        map.put("fecha", orden.getFecha());

        // Convertir usuario a representación lógica
        if (orden.getUsuario() != null) {
            Map<String, Object> usuarioMap = new HashMap<>();
            Usuario usuario = orden.getUsuario();
            usuarioMap.put("id", usuario.getId());
            usuarioMap.put("nombre", usuario.getNombre());
            usuarioMap.put("email", usuario.getEmail());
            map.put("usuario", usuarioMap);
        }

        // Convertir detalles a representación lógica
        if (orden.getDetalle() != null && !orden.getDetalle().isEmpty()) {
            List<Map<String, Object>> detallesMap = new ArrayList<>();
            for (DetalleOrden detalle : orden.getDetalle()) {
                Map<String, Object> detalleMap = new HashMap<>();
                detalleMap.put("id", detalle.getId());
                detalleMap.put("cantidad", detalle.getCantidad());

                if (detalle.getProducto() != null) {
                    Map<String, Object> productoMap = new HashMap<>();
                    productoMap.put("id", detalle.getProducto().getId());
                    productoMap.put("nombre", detalle.getProducto().getNombre());
                    detalleMap.put("producto", productoMap);
                }

                detallesMap.add(detalleMap);
            }
            map.put("detalles", detallesMap);
        }

        return map;
    }
}
