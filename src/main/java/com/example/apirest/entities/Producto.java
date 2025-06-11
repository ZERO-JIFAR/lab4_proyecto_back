package com.example.apirest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto extends Base {

    private String nombre;
    private Integer cantidad;
    private Double precio;
    private String descripcion;
    private String color;
    private String marca;

    // Campo para la imagen principal
    private String imagenUrl;

    // Lista de imágenes adicionales (opcional)
    @ElementCollection
    @CollectionTable(name = "producto_imagenes", joinColumns = @JoinColumn(name = "producto_id"))
    @Column(name = "imagen_url")
    private List<String> imagenesAdicionales = new ArrayList<>();

    @ManyToOne
    private Categoria categoria;

    // Relación con TalleProducto para manejar los talles y stock
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalleProducto> tallesProducto = new ArrayList<>();
}
