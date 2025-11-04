package com.Pasteleria.Pasteleria.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.Pasteleria.Pasteleria.model.Producto;

@Repository
public class ProductoRepository {

    private final List<Producto> listaProductos = new ArrayList<>();

    public List<Producto> obtenerProductos() {
        return listaProductos;
    }

    public Producto guardar(Producto nuevo) {
        eliminar(nuevo.getCodigo());
        listaProductos.add(nuevo);
        return nuevo;
    }

    public Producto buscarPorId(int codigo) {
        Optional<Producto> p = listaProductos.stream()
                .filter(prod -> prod.getCodigo() == codigo)
                .findFirst();
        return p.orElse(null);
    }

    public Producto actualizar(Producto cur) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getCodigo() == cur.getCodigo()) {
                listaProductos.set(i, cur);
                return cur;
            }
        }
        listaProductos.add(cur);
        return cur;
    }

    public void eliminar(int codigo) {
        listaProductos.removeIf(producto -> producto.getCodigo() == codigo);
    }
}
