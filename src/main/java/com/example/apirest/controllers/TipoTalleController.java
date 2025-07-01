package com.example.apirest.controllers;

import com.example.apirest.entities.TipoTalle;
import com.example.apirest.services.TipoTalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tiposTalle")
public class TipoTalleController {

    @Autowired
    private TipoTalleService tipoTalleService;

    @GetMapping
    public ResponseEntity<List<TipoTalle>> getAll() {
        return ResponseEntity.ok(tipoTalleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTalle> getOne(@PathVariable Long id) {
        Optional<TipoTalle> tipoTalle = tipoTalleService.findById(id);
        return tipoTalle.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoTalle> create(@RequestBody TipoTalle tipoTalle) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipoTalleService.save(tipoTalle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTalle> update(@PathVariable Long id, @RequestBody TipoTalle tipoTalle) {
        if (!tipoTalleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tipoTalle.setId(id);
        return ResponseEntity.ok(tipoTalleService.save(tipoTalle));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TipoTalle> patchTipoTalle(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<TipoTalle> optional = tipoTalleService.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        TipoTalle tipoTalle = optional.get();
        if (updates.containsKey("eliminado")) {
            tipoTalle.setEliminado(Boolean.TRUE.equals(updates.get("eliminado")) || "true".equals(updates.get("eliminado")));
        }
        // Puedes agregar más campos editables aquí si lo deseas
        TipoTalle actualizado = tipoTalleService.save(tipoTalle);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!tipoTalleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tipoTalleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
