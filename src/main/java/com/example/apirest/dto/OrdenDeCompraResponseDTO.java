package com.example.apirest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrdenDeCompraResponseDTO {
    private Long id;
    private LocalDate fecha;
    private String estadoPago;
    private List<DetalleOrdenDTO> detalle;

    public static class DetalleOrdenDTO {
        private ProductoDTO producto;
        private Integer cantidad;
        private BigDecimal precioUnitario;

        // Getters y setters
        public ProductoDTO getProducto() { return producto; }
        public void setProducto(ProductoDTO producto) { this.producto = producto; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
        public BigDecimal getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    }

    public static class ProductoDTO {
        private String nombre;
        private String imagenUrl;
        private String color;

        // Getters y setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getImagenUrl() { return imagenUrl; }
        public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
    public List<DetalleOrdenDTO> getDetalle() { return detalle; }
    public void setDetalle(List<DetalleOrdenDTO> detalle) { this.detalle = detalle; }
}