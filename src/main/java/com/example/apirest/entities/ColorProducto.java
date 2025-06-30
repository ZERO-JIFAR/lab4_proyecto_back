package com.example.apirest.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorProducto extends Base {

    private String color;
    private String imagenUrl;

    @ElementCollection
    private List<String> imagenesAdicionales;

    @ManyToOne
    private Producto producto;

    @OneToMany(mappedBy = "colorProducto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalleColorProducto> tallesColor;
}