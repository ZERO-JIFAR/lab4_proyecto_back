package com.example.apirest.config;

import com.example.apirest.entities.Usuario;
import com.example.apirest.entities.enums.Rol;
import com.example.apirest.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initializeAdmin() {
        return args -> {
            // Check if admin user already exists
            if (usuarioRepository.findByEmail("admin@admin.com") == null) {
                // Create admin user
                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setEmail("admin@admin.com");
                admin.setContrasena(passwordEncoder.encode("admin123"));
                admin.setRol(Rol.ADMIN);
                
                // Save admin user
                usuarioRepository.save(admin);
                
                System.out.println("Admin user created successfully with email: admin@admin.com and password: admin123");
            } else {
                System.out.println("Admin user already exists");
            }
        };
    }
}