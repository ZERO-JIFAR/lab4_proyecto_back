package com.example.apirest.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrdenDeCompraDTO {
    private Long usuarioId;
    private List<ItemOrdenDTO> items;
    private String tipoEntrega;
    private String direccion;
    private String ciudad;

    @Data
    public static class ItemOrdenDTO {
        private Long productoId;
        private int cantidad;
        private String talle;
        private String color;
        private double precioUnitario;
    }
}