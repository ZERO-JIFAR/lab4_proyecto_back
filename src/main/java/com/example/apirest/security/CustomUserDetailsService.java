package com.example.apirest.security;

import com.example.apirest.entities.Usuario;
import com.example.apirest.repositories.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar usuario por email y que no esté eliminado
        Usuario usuario = usuarioRepository.findByEmailAndEliminadoFalse(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado o deshabilitado con email: " + email);
        }

        return new User(
                usuario.getEmail(),
                usuario.getContrasena(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
        );
    }
}