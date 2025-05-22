package com.example.apirest.controllers;

import com.example.apirest.entities.Categoria;
import com.example.apirest.services.CategoriaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/categorias")
public class CategoriaController extends BaseController<Categoria, Long> {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        super(categoriaService);
        this.categoriaService = categoriaService;
    }

    @Override
    protected Map<String, Object> convertToMap(Categoria entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", entity.getId());
        map.put("nombre", entity.getNombre());
        map.put("descripcion", entity.getDescripcion());
        return map;
    }

    @Override
    protected Categoria convertToEntity(Map<String, Object> entityMap) {
        Categoria categoria = new Categoria();
        if (entityMap.containsKey("id")) {
            categoria.setId(Long.valueOf(entityMap.get("id").toString()));
        }
        if (entityMap.containsKey("nombre")) {
            categoria.setNombre((String) entityMap.get("nombre"));
        }
        if (entityMap.containsKey("descripcion")) {
            categoria.setDescripcion((String) entityMap.get("descripcion"));
        }
        return categoria;
    }
}
