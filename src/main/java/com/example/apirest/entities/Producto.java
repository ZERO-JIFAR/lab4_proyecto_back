package com.example.apirest.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto extends Base {

    private String nombre;
    private Integer cantidad;
    private Double precio;
    private Double precioOriginal;
    private String descripcion;
    private String marca;
    private String imagenUrl; // Imagen general (opcional)

    // El campo eliminado ya está en Base, NO lo declares aquí como Boolean

    @ManyToOne
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ColorProducto> colores; // Relación con los colores disponibles
}