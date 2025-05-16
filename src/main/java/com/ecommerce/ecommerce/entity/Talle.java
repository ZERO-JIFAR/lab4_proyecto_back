package com.ecommerce.ecommerce.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Talle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTalle;
    private String tipoTalle;

    @ManyToOne
    private Tipo tipo;
}

