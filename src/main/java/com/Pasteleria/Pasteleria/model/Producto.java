package com.Pasteleria.Pasteleria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private int codigo;
    private String categoria;
    private String nombre;
    private BigDecimal precio;
}
