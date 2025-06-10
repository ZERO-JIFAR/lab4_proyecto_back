package com.example.apirest.controllers;

import com.example.apirest.entities.Usuario;
import com.example.apirest.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /**
     * Endpoint para promover un usuario a rol de administrador
     * Solo accesible por usuarios con rol ADMIN
     */
    @PostMapping("/{id}/promoverAAdmin")
    public ResponseEntity<Usuario> promoverAAdmin(@PathVariable Long id) throws Exception {
        Usuario usuarioActualizado = usuarioService.promoverAAdmin(id);
        return ResponseEntity.ok(usuarioActualizado);
    }
}
