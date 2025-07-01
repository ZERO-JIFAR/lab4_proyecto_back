package com.example.apirest.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private Integer cantidad;
    private Double precio;
    private Double precioOriginal;
    private String descripcion;
    private String marca;
    private String imagenUrl;
    private CategoriaDTO categoria;
    private List<ColorDTO> colores;
    private boolean eliminado; // <-- PRIMITIVO

    @Data
    public static class CategoriaDTO {
        private Long id;
        private String nombre;
        private TipoDTO tipo;
    }

    @Data
    public static class TipoDTO {
        private Long id;
        private String nombre;
    }

    @Data
    public static class ColorDTO {
        private Long id;
        private String color;
        private String imagenUrl;
        private List<String> imagenesAdicionales;
        private List<TalleStockDTO> talles;
    }

    @Data
    public static class TalleStockDTO {
        private Long talleId;
        private String talleValor;
        private Integer stock;
    }
}