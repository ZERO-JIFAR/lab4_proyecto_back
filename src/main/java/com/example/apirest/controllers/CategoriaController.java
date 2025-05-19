package com.example.apirest.controllers;

import com.example.apirest.entities.Categoria;
import com.example.apirest.services.CategoriaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaController extends BaseController<Categoria, Long> {

    public CategoriaController(CategoriaService categoriaService) {
        super(categoriaService);
    }
}
