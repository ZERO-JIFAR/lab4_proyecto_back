package com.example.apirest.services;

import com.example.apirest.dto.AuthResponse;
import com.example.apirest.dto.LoginRequest;
import com.example.apirest.dto.RegisterRequest;
import com.example.apirest.entities.Usuario;
import com.example.apirest.entities.enums.Rol;
import com.example.apirest.repositories.UsuarioRepository;
import com.example.apirest.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, 
                      JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setContrasena(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.USER); // Por defecto, todos los usuarios nuevos son USER

        // Guardar usuario
        usuarioRepository.save(usuario);

        // Generar token
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());

        // Retornar respuesta
        return AuthResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .rol(usuario.getRol().name())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // Autenticar usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Si llegamos aquí, la autenticación fue exitosa
        // Buscar usuario
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail());
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Generar token
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());

        // Retornar respuesta
        return AuthResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .rol(usuario.getRol().name())
                .build();
    }
}