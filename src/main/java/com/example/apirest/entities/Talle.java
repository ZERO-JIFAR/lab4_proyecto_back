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

    // Puede ser un valor numérico (para calzado) o un string (S, M, L, XL)
    private String valor;

    // Relación con el tipo de talle
    @ManyToOne
    private TipoTalle tipoTalle;
}