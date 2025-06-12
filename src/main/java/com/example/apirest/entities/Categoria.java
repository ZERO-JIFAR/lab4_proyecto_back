package com.example.apirest.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Categoria extends Base {

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @ManyToOne
    private Tipo tipo;  // Cambiado de idTipo a tipo
}
