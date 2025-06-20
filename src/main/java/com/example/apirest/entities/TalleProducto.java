package com.example.apirest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalleProducto extends Base {

    @ManyToOne
    @JsonBackReference
    private Producto producto;

    @ManyToOne
    private Talle talle;

    private Integer stock;
}
