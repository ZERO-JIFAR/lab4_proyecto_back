package com.example.apirest.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductoUpdateDTO {
    private String nombre;
    private Double precio;
    private Double precioOriginal;
    private String descripcion;
    private String marca;
    private String imagenUrl;
    private Long categoriaId;
    private Boolean eliminado;

}