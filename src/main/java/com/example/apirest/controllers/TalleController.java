package com.example.apirest.controllers;

import com.example.apirest.entities.Talle;
import com.example.apirest.services.TalleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/talles")
public class TalleController extends BaseController<Talle, Long> {

    private final TalleService talleService;

    public TalleController(TalleService talleService) {
        super(talleService);
        this.talleService = talleService;
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<List<Talle>> obtenerPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(talleService.obtenerPorId(id));
    }

    @GetMapping("/tipoTalle/{tipoTalleId}")
    public ResponseEntity<List<Talle>> obtenerPorTipoTalle(@PathVariable Long tipoTalleId){
        return ResponseEntity.ok(talleService.obtenerPorTipoTalle(tipoTalleId));
    }

    // PATCH /talles/{id} para soft delete/habilitar
    @PatchMapping("/{id}")
    public ResponseEntity<Talle> patchTalle(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws Exception {
        Optional<Talle> optional = talleService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Talle talle = optional.get();
        if (updates.containsKey("eliminado")) {
            talle.setEliminado(Boolean.TRUE.equals(updates.get("eliminado")) || "true".equals(updates.get("eliminado")));
        }
        Talle actualizado = talleService.actualizar(talle);
        return ResponseEntity.ok(actualizado);
    }
}