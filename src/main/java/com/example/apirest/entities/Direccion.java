package com.example.apirest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Direccion extends Base {


    private String calle;
    private String numero;
    private String ciudad;
    private String provincia;
    private String pais;
    private String codigoPostal;
}
