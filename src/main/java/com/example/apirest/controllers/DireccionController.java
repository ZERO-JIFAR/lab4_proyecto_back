package com.example.apirest.controllers;

import com.example.apirest.entities.Direccion;
import com.example.apirest.services.DireccionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController extends BaseController<Direccion, Long> {

    public DireccionController(DireccionService direccionService) {
        super(direccionService);
    }
}
