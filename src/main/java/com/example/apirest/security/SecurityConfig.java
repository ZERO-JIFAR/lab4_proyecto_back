package com.example.apirest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        // --- PRODUCTOS ---
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/productos/dto/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/productos/*/restar-stock").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/productos/con-colores").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/productos/con-colores/**").hasAuthority("ROLE_ADMIN") // <-- AGREGADO
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/productos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/productos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/productos/*/descuento").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/productos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/productos/**").hasAuthority("ROLE_ADMIN")
                        // --- CATEGORIAS Y TIPOS ---
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/categorias").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/categorias/{id}").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/tipos").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/tipos/{id}").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/categorias/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/categorias/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/categorias/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/categorias/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/tipos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/tipos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/tipos/**").hasAuthority("ROLE_ADMIN") // <-- AGREGADO
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/tipos/**").hasAuthority("ROLE_ADMIN")
                        // --- TALLES ---
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/talles/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/talles-producto/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/talles/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/talles/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/talles/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/talles-producto/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/talles-producto/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/talles-producto/**").hasAuthority("ROLE_ADMIN")
                        // --- ORDENES Y PAGOS ---
                        .requestMatchers("/ordenes/**").permitAll()
                        .requestMatchers("/api/ordenes/**").permitAll()
                        .requestMatchers("/pay/**").permitAll()
                        .requestMatchers("/api/ordenes").permitAll()
                        // --- USUARIOS ---
                        .requestMatchers("/usuarios/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/usuarioDireccion/**").hasAuthority("ROLE_ADMIN")
                        // --- TIPOS DE TALLE ---
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/tiposTalle").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/tiposTalle/{id}").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/tiposTalle/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/tiposTalle/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/tiposTalle/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/tiposTalle/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(java.util.Arrays.asList("http://localhost:5173", "https://localhost:5173"));
        configuration.setAllowedMethods(java.util.Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(java.util.Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
