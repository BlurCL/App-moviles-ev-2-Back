package com.Pasteleria.Pasteleria.config;

import com.Pasteleria.Pasteleria.model.Producto;
import com.Pasteleria.Pasteleria.model.Rol;
import com.Pasteleria.Pasteleria.model.Usuario;
import com.Pasteleria.Pasteleria.repository.ProductoRepository;
import com.Pasteleria.Pasteleria.repository.RolRepository;
import com.Pasteleria.Pasteleria.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository, 
                           UsuarioRepository usuarioRepository,
                           ProductoRepository productoRepository,
                           PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("--- INICIANDO CARGA DE DATOS DE PRUEBA ---");

        // 1. Crear Roles si no existen
        crearRolSiNoExiste("ROLE_ADMIN");
        crearRolSiNoExiste("ROLE_CLIENTE");
        crearRolSiNoExiste("ROLE_VENDEDOR");

        // 2. Crear Usuario Admin de prueba
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setUsername("admin");
            admin.setEmail("admin@pasteleria.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Pass: admin123
            
            // Asignar rol ADMIN
            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN").orElseThrow();
            admin.setRoles(Collections.singleton(rolAdmin));
            
            usuarioRepository.save(admin);
            System.out.println(">>> Usuario 'admin' creado (Pass: admin123)");
        }

        // 3. Crear Usuario Cliente de prueba
        if (!usuarioRepository.existsByUsername("cliente")) {
            Usuario cliente = new Usuario();
            cliente.setNombre("Cliente Prueba");
            cliente.setUsername("cliente");
            cliente.setEmail("cliente@gmail.com");
            cliente.setPassword(passwordEncoder.encode("cliente123")); // Pass: cliente123
            
            Rol rolCliente = rolRepository.findByNombre("ROLE_CLIENTE").orElseThrow();
            cliente.setRoles(Collections.singleton(rolCliente));
            
            usuarioRepository.save(cliente);
            System.out.println(">>> Usuario 'cliente' creado (Pass: cliente123)");
        }

        // 4. Crear Productos de prueba (para que tu App móvil no se vea vacía)
        if (productoRepository.count() == 0) {
            productoRepository.save(new Producto(1, "Tortas", "Torta Selva Negra", new BigDecimal(15000), "https://robohash.org/torta1"));
            productoRepository.save(new Producto(2, "Pasteles", "Pie de Limón", new BigDecimal(8500), "https://robohash.org/pie1"));
            productoRepository.save(new Producto(3, "Galletas", "Caja Galletas Finas", new BigDecimal(4000), "https://robohash.org/galletas"));
            System.out.println(">>> 3 Productos de prueba creados");
        }
        
        System.out.println("--- CARGA DE DATOS FINALIZADA ---");
    }

    private void crearRolSiNoExiste(String nombreRol) {
        if (rolRepository.findByNombre(nombreRol).isEmpty()) {
            Rol rol = new Rol();
            rol.setNombre(nombreRol);
            rolRepository.save(rol);
            System.out.println(">>> Rol creado: " + nombreRol);
        }
    }
}