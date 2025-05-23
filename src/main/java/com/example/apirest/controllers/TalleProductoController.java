package com.example.apirest.controllers;

import com.example.apirest.entities.Producto;
import com.example.apirest.entities.Talle;
import com.example.apirest.entities.TalleProducto;
import com.example.apirest.entities.Tipo;
import com.example.apirest.services.TalleProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/talles-producto")
public class TalleProductoController {

    private final TalleProductoService talleProductoService;

    public TalleProductoController(TalleProductoService talleProductoService) {
        this.talleProductoService = talleProductoService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() throws Exception {
        List<TalleProducto> tallesProducto = talleProductoService.listar();
        List<Map<String, Object>> result = tallesProducto.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Map<String, Object>>> porProducto(@PathVariable Long productoId) {
        List<TalleProducto> tallesProducto = talleProductoService.obtenerPorProducto(productoId);
        List<Map<String, Object>> result = tallesProducto.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/producto/{productoId}/talle/{talleId}")
    public ResponseEntity<Map<String, Object>> porProductoYTalle(@PathVariable Long productoId, @PathVariable Long talleId) throws Exception {
        TalleProducto talleProducto = talleProductoService.obtenerPorProductoYTalle(productoId, talleId);
        return ResponseEntity.ok(convertToMap(talleProducto));
    }

    @GetMapping("/disponibles/{productoId}")
    public ResponseEntity<List<Map<String, Object>>> disponiblesPorProducto(@PathVariable Long productoId) {
        List<TalleProducto> tallesProducto = talleProductoService.obtenerDisponiblesPorProducto(productoId);
        List<Map<String, Object>> result = tallesProducto.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    //  convertir entidad a Map (representación lógica)
    private Map<String, Object> convertToMap(TalleProducto talleProducto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", talleProducto.getId());

        // Convertir producto a representación lógica
        if (talleProducto.getProducto() != null) {
            Map<String, Object> productoMap = new HashMap<>();
            Producto producto = talleProducto.getProducto();
            productoMap.put("id", producto.getId());
            productoMap.put("nombre", producto.getNombre());
            productoMap.put("precio", producto.getPrecio());
            map.put("producto", productoMap);
        }

        // Convertir talle a representación lógica
        if (talleProducto.getTalle() != null) {
            Map<String, Object> talleMap = new HashMap<>();
            Talle talle = talleProducto.getTalle();
            talleMap.put("id", talle.getId());
            talleMap.put("tipoTalle", talle.getTipoTalle());

            // Convertir tipo a representación lógica
            if (talle.getTipo() != null) {
                Map<String, Object> tipoMap = new HashMap<>();
                Tipo tipo = talle.getTipo();
                tipoMap.put("id", tipo.getId());
                tipoMap.put("nombre", tipo.getNombre());
                talleMap.put("tipo", tipoMap);
            }

            map.put("talle", talleMap);
        }

        return map;
    }
}
