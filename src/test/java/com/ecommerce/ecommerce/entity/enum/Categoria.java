package com.tuempresa.tuproducto.model;

public enum Categoria {
    DEPORTIVO("Zapatos para actividades deportivas"),
    CASUAL("Zapatos para uso diario"),
    FORMAL("Zapatos elegantes para eventos o trabajo"),
    SANDALIAS("Zapatos abiertos para clima cálido"),
    BOTAS("Zapatos cerrados y altos para clima frío o moda");

    private final String descripcion;

    Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
