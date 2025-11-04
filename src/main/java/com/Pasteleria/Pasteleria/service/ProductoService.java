package com.Pasteleria.Pasteleria.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.Pasteleria.Pasteleria.model.Producto;
import com.Pasteleria.Pasteleria.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> getProductos() {
        return productoRepository.obtenerProductos();
    }

    public Producto saveProducto(Producto producto) {
        return productoRepository.guardar(producto);
    }

    public Producto getProductoId(int id) {
        return productoRepository.buscarPorId(id);
    }

    public Producto updateProducto(Producto producto) {
        return productoRepository.actualizar(producto);
    }

    public String deleteProducto(int id) {
        productoRepository.eliminar(id);
        return "Producto eliminado";
    }
}
