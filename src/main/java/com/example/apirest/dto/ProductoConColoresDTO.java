package com.example.apirest.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductoConColoresDTO {
    private String nombre;
    private Double precio;
    private Double precioOriginal;
    private String descripcion;
    private String marca;
    private String imagenUrl;
    private Long categoriaId;
    private List<ColorDTO> colores;

    @Data
    public static class ColorDTO {
        private String color;
        private String imagenUrl;
        private List<String> imagenesAdicionales;
        private List<TalleStockDTO> talles;
    }

    @Data
    public static class TalleStockDTO {
        private Long talleId;
        private Integer stock;
    }
}