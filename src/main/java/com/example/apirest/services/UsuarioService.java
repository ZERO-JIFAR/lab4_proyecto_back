package com.example.apirest.services;

import com.example.apirest.entities.Usuario;
import com.example.apirest.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends BaseService<Usuario, Long> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
        this.usuarioRepository = usuarioRepository;
    }

    // Puedes métodos específicos del Usuario aquí
    public Usuario buscarPorEmail(String email) throws Exception {
        try {
            return usuarioRepository.findByEmail(email);
        } catch (Exception ex) {
            throw new Exception("Error al buscar usuario por email: " + ex.getMessage());
        }
    }
}

