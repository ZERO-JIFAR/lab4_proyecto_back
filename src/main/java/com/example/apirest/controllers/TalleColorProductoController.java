package com.example.apirest.controllers;

import com.example.apirest.entities.TalleColorProducto;
import com.example.apirest.services.TalleColorProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talles-color-producto")
public class TalleColorProductoController {

    private final TalleColorProductoService talleColorProductoService;

    public TalleColorProductoController(TalleColorProductoService talleColorProductoService) {
        this.talleColorProductoService = talleColorProductoService;
    }

    @GetMapping
    public ResponseEntity<List<TalleColorProducto>> getAll() throws Exception {
        return ResponseEntity.ok(talleColorProductoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TalleColorProducto> getById(@PathVariable Long id) throws Exception {
        return talleColorProductoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/color/{colorProductoId}")
    public ResponseEntity<List<TalleColorProducto>> getByColorProducto(@PathVariable Long colorProductoId) {
        return ResponseEntity.ok(talleColorProductoService.obtenerPorColorProducto(colorProductoId));
    }

    @GetMapping("/color/{colorProductoId}/talle/{talleId}")
    public ResponseEntity<TalleColorProducto> getByColorAndTalle(@PathVariable Long colorProductoId, @PathVariable Long talleId) {
        return talleColorProductoService.obtenerPorColorYtalle(colorProductoId, talleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/disponibles/{colorProductoId}")
    public ResponseEntity<List<TalleColorProducto>> getDisponiblesPorColorProducto(@PathVariable Long colorProductoId) {
        return ResponseEntity.ok(talleColorProductoService.obtenerDisponiblesPorColorProducto(colorProductoId));
    }

    @PostMapping
    public ResponseEntity<TalleColorProducto> create(@RequestBody TalleColorProducto talleColorProducto) throws Exception {
        return ResponseEntity.ok(talleColorProductoService.crear(talleColorProducto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TalleColorProducto> update(@PathVariable Long id, @RequestBody TalleColorProducto talleColorProducto) throws Exception {
        talleColorProducto.setId(id);
        return ResponseEntity.ok(talleColorProductoService.actualizar(talleColorProducto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        talleColorProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
