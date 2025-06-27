package com.example.apirest.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleOrden extends Base {
    @ManyToOne
    private Producto producto;

    @ManyToOne
    private OrdenDeCompra orden;

    private int cantidad;
    private BigDecimal precioUnitario;
    private String talle;
    private String color;
}