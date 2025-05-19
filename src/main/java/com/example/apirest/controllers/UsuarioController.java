package com.example.apirest.controllers;

import com.example.apirest.entities.Usuario;
import com.example.apirest.services.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends BaseController<Usuario, Long> {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        super(usuarioService);
        this.usuarioService = usuarioService;
    }

    // Ejemplo de endpoint personalizado: buscar usuario por email
    @GetMapping("/buscarPorEmail")
    public Usuario buscarPorEmail(@RequestParam String email) throws Exception {
        return usuarioService.buscarPorEmail(email);
    }
}
