package com.Pasteleria.Pasteleria.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // =====================
                // SWAGGER / DOCS / H2
                // =====================
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/h2-console/**"  // <--- AGREGAR ESTO: Permite entrar a la URL de H2
                ).permitAll()

                // =====================
                // AUTENTICACIÓN
                // =====================
                .requestMatchers("/api/v1/auth/**").permitAll()

                // =====================
                // PRODUCTOS
                // =====================
                .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").permitAll()
                .requestMatchers("/api/v1/productos/**").hasRole("ADMIN")

                // =====================
                // ÓRDENES
                // =====================
                .requestMatchers(HttpMethod.POST, "/api/v1/ordenes/**").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET, "/api/v1/ordenes/**").hasAnyRole("ADMIN", "VENDEDOR")

                // =====================
                // RESTO DE ENDPOINTS
                // =====================
                .anyRequest().authenticated()
            )
            // <--- AGREGAR ESTO: Permite que H2 use "frames" (sin esto, la pantalla se queda en blanco o error)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        // Autenticación con nuestro UserDetailsService + BCrypt
        http.authenticationProvider(authenticationProvider());

        // Filtro JWT antes del filtro de username/password
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}