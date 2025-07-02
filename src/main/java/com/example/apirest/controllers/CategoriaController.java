package com.example.apirest.controllers;

import com.example.apirest.entities.Categoria;
import com.example.apirest.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController extends BaseController<Categoria, Long> {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        super(categoriaService);
        this.categoriaService = categoriaService;
    }

    // PATCH único para restaurar/deshabilitar sin chocar con BaseController
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Categoria> patchCategoriaEstado(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws Exception {
        Optional<Categoria> optional = categoriaService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Categoria categoria = optional.get();
        if (updates.containsKey("eliminado")) {
            categoria.setEliminado(Boolean.TRUE.equals(updates.get("eliminado")) || "true".equals(updates.get("eliminado")));
        }
        Categoria actualizado = categoriaService.actualizar(categoria);
        return ResponseEntity.ok(actualizado);
    }
}
