package com.Pasteleria.Pasteleria.controller;

import java.util.List;
import org.springframework.http.ResponseEntity; // Importante
import org.springframework.web.bind.annotation.*;
import com.Pasteleria.Pasteleria.model.Producto;
import com.Pasteleria.Pasteleria.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/productos") 
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
@CrossOrigin(origins = {"*"})
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Listar productos")
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.getProductos());
    }

    @PostMapping
    @Operation(summary = "Ingresar producto")
    public ResponseEntity<Producto> agregarProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.saveProducto(producto));
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Buscar producto por código")
    public ResponseEntity<Producto> buscarProducto(@PathVariable int codigo) {
        Producto producto = productoService.getProductoId(codigo);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        }
        return ResponseEntity.notFound().build(); // Devuelve 404 si no existe
    }

    @PutMapping("/{codigo}")
    @Operation(summary = "Actualizar producto por código")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable int codigo, @RequestBody Producto producto) {
        producto.setCodigo(codigo); // Aseguramos que el ID sea el de la URL
        return ResponseEntity.ok(productoService.updateProducto(producto));
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Eliminar producto por código")
    public ResponseEntity<String> eliminarProducto(@PathVariable int codigo) {
        productoService.deleteProducto(codigo);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }
}