package com.example.apirest.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalleColorProducto extends Base {

    @ManyToOne
    private ColorProducto colorProducto;

    @ManyToOne
    private Talle talle;

    private Integer stock;
}