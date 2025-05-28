package com.example.apirest.controllers;

import com.example.apirest.entities.Tipo;
import com.example.apirest.services.TipoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipos")
public class TipoController extends BaseController<Tipo, Long> {

    public TipoController(TipoService tipoService) {
        super(tipoService);
    }
}