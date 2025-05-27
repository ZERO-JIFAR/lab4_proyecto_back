package com.example.apirest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrden extends Base {


    @ManyToOne
    private OrdenDeCompra orden;

    @ManyToOne
    private Producto producto;

    private Integer cantidad;
}
