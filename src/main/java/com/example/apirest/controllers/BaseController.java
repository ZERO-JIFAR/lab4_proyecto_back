package com.example.apirest.controllers;
import com.example.apirest.entities.Base;
import com.example.apirest.services.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseController<E extends Base, ID extends Serializable> {

    protected BaseService<E, ID> service;

    public BaseController(BaseService<E, ID> service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<E>> listar() throws Exception {
        List<E> entities = service.listar();
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public Optional<E> buscarPorId(@PathVariable ID id) throws Exception {
        return service.buscarPorId(id);
    }

    @PostMapping()
    public ResponseEntity<E> crear(@RequestBody E entity) throws Exception {
        E entidadCreada = service.crear(entity);
        return ResponseEntity.ok(entidadCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<E> actualizar(@PathVariable ID id, @RequestBody E entity) throws Exception {
        // Asegurarse de que el ID en la URL coincida con el ID en el cuerpo
        if (entity instanceof Base) {
            ((Base) entity).setId((Long) id);
        }
        E entidadAct = service.actualizar(entity);
        return ResponseEntity.ok(entidadAct);
    }

    // Añadir un endpoint alternativo sin ID en la URL para compatibilidad
    @PutMapping()
    public ResponseEntity<E> actualizarSinId(@RequestBody E entity) throws Exception {
        E entidadAct = service.actualizar(entity);
        return ResponseEntity.ok(entidadAct);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable ID id) throws Exception {
        service.eliminar(id);
    }

}
