package com.example.apirest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Talle extends Base {

    // Agregar campo para el valor numérico del talle
    private Double valor;

    @ManyToOne
    private Tipo tipoTalle;
}