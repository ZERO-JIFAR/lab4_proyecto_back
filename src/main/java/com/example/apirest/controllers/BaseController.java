package com.example.apirest.controllers;
import com.example.apirest.entities.Base;
import com.example.apirest.services.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseController<E extends Base, ID extends Serializable> {

    protected BaseService<E, ID> service;

    public BaseController(BaseService<E, ID> service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Map<String, Object>>> listar() throws Exception {
        List<E> entities = service.listar();
        List<Map<String, Object>> result = entities.stream()
            .map(this::convertToMap)
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable ID id) throws Exception {
        Optional<E> optEntity = service.buscarPorId(id);
        if (optEntity.isPresent()) {
            return ResponseEntity.ok(convertToMap(optEntity.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Map<String, Object> entityMap) throws Exception {
        E entity = convertToEntity(entityMap);
        E entidadCreada = service.crear(entity);
        return ResponseEntity.ok(convertToMap(entidadCreada));
    }

    @PutMapping()
    public ResponseEntity<Map<String, Object>> actualizar(@RequestBody Map<String, Object> entityMap) throws Exception {
        E entity = convertToEntity(entityMap);
        E entidadAct = service.actualizar(entity);
        return ResponseEntity.ok(convertToMap(entidadAct));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable ID id) throws Exception {
        service.eliminar(id);
    }

    // Método para convertir entidad a Map (representación lógica)
    protected Map<String, Object> convertToMap(E entity) {
        // Implementación básica que debe ser sobrescrita por las clases hijas
        Map<String, Object> map = new HashMap<>();
        map.put("id", entity.getId());
        return map;
    }

    // Método para convertir Map a entidad
    protected abstract E convertToEntity(Map<String, Object> entityMap);
}
