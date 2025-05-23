package com.example.apirest.controllers;

import com.example.apirest.entities.Usuario;
import com.example.apirest.entities.enums.Rol;
import com.example.apirest.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends BaseController<Usuario, Long> {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        super(usuarioService);
        this.usuarioService = usuarioService;
    }

    // endpoint personalizado: buscar usuario por email
    @GetMapping("/buscarPorEmail")
    public ResponseEntity<Map<String, Object>> buscarPorEmail(@RequestParam String email) throws Exception {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        if (usuario != null) {
            return ResponseEntity.ok(convertToMap(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    protected Map<String, Object> convertToMap(Usuario entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", entity.getId());
        map.put("nombre", entity.getNombre());
        map.put("email", entity.getEmail());
        map.put("rol", entity.getRol().toString());
        // No incluimos la contraseña por seguridad
        return map;
    }

    @Override
    protected Usuario convertToEntity(Map<String, Object> entityMap) {
        Usuario usuario = new Usuario();
        if (entityMap.containsKey("id")) {
            usuario.setId(Long.valueOf(entityMap.get("id").toString()));
        }
        if (entityMap.containsKey("nombre")) {
            usuario.setNombre((String) entityMap.get("nombre"));
        }
        if (entityMap.containsKey("email")) {
            usuario.setEmail((String) entityMap.get("email"));
        }
        if (entityMap.containsKey("contrasena")) {
            usuario.setContrasena((String) entityMap.get("contrasena"));
        }
        if (entityMap.containsKey("rol")) {
            usuario.setRol(Rol.valueOf((String) entityMap.get("rol")));
        }
        return usuario;
    }
}
