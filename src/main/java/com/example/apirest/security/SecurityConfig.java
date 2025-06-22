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
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/productos").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/productos/{id}").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/productos/buscar").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/productos/categoria/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/productos/disponibles/talle/**").permitAll()
                        // Permitir GET para categorías y tipos a todos los usuarios
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/categorias").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/categorias/{id}").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/tipos").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/tipos/{id}").permitAll()
                        // NUEVAS REGLAS: Permitir acceso a endpoints de talles para todos los usuarios
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/talles/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/talles-producto/**").permitAll()
                        // MODIFICADO: Permitir acceso a endpoints de compra para todos los usuarios
                        .requestMatchers("/ordenes/**").permitAll()
                        .requestMatchers("/pay/**").permitAll()
                        // Mantener restricciones de admin para otros endpoints
                        .requestMatchers("/usuarios/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/categorias/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/categorias/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/categorias/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/tipos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/tipos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/tipos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/detalleOrden/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/direcciones/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/ordenDeCompra/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/productos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/talles/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/talles/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/talles/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/talles-producto/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/talles-producto/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/talles-producto/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/usuarioDireccion/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/tiposTalle").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/tiposTalle/{id}").permitAll()
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
        configuration.setAllowedOrigins(java.util.Collections.singletonList("http://localhost:5173"));
        configuration.setAllowedMethods(java.util.Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(java.util.Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
