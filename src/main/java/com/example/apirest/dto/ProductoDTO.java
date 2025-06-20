package com.example.apirest.dto;

import com.example.apirest.entities.Categoria;
import com.example.apirest.entities.TalleProducto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private Integer cantidad;
    private Double precio;
    private String descripcion;
    private String color;
    private String marca;
    private String imagenUrl;
    private List<String> imagenesAdicionales = new ArrayList<>();
    private Categoria categoria;

    // Original field
    private List<TalleProducto> tallesProducto = new ArrayList<>();

    // New field for frontend compatibility
    private List<TalleProducto> talles = new ArrayList<>();
}
