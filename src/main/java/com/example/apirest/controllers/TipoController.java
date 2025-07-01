package com.example.apirest.controllers;

import com.example.apirest.entities.Tipo;
import com.example.apirest.services.TipoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tipos")
public class TipoController extends BaseController<Tipo, Long> {

    public TipoController(TipoService tipoService) {
        super(tipoService);
    }

    // PATCH /tipos/{id} para soft delete/habilitar
    @PatchMapping("/{id}")
    public ResponseEntity<Tipo> patchTipo(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws Exception {
        Tipo tipo = service.buscarPorId(id).orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
        if (updates.containsKey("eliminado")) {
            tipo.setEliminado(Boolean.TRUE.equals(updates.get("eliminado")) || "true".equals(updates.get("eliminado")));
        }
        // Puedes agregar más campos editables aquí si lo deseas
        Tipo actualizado = service.actualizar(tipo);
        return ResponseEntity.ok(actualizado);
    }
}