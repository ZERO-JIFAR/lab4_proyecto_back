package com.example.apirest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoTalle extends Base {

    @Column(nullable = false)
    private String nombre;  // Por ejemplo: "Calzado US", "Ropa", etc.

    private String descripcion;
}