package com.example.apirest.controllers;

import com.example.apirest.entities.Talle;
import com.example.apirest.services.TalleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/talles")
public class TalleController {

    private final TalleService talleService;

    public TalleController(TalleService talleService) {
        this.talleService = talleService;
    }

    @GetMapping
    public ResponseEntity<List<Talle>> getAll() throws Exception {
        return ResponseEntity.ok(talleService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Talle>> obtenerPorId(@PathVariable Long Id){
        return ResponseEntity.ok(talleService.obtenerPorId(Id));
    }

    @GetMapping("/tipoTalle/{tipoTalleId}")
    public ResponseEntity<List<Talle>> obtenerPorTipoTalle(@PathVariable Long tipoTalleId){
        return ResponseEntity.ok(talleService.obtenerPorTipoTalle(tipoTalleId));
    }
}
