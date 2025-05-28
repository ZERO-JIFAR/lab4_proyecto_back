package com.example.apirest.controllers;

import com.example.apirest.entities.UsuarioDireccion;
import com.example.apirest.services.UsuarioDireccionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario-direcciones")
public class UsuarioDireccionController extends BaseController<UsuarioDireccion, Long> {

    public UsuarioDireccionController(UsuarioDireccionService usuarioDireccionService) {
        super(usuarioDireccionService);
    }
}