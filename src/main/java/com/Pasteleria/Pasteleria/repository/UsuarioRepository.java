package com.Pasteleria.Pasteleria.repository;

import com.Pasteleria.Pasteleria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Buscar por email (existente)
    Optional<Usuario> findByEmail(String email);
    Boolean existsByEmail(String email);

    // === MÉTODOS NUEVOS QUE FALTABAN ===
    Optional<Usuario> findByUsername(String username);
    Boolean existsByUsername(String username);
    // ===================================
}