package com.example.apirest.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
    private Long ordenId;
    private String descripcion;
    private BigDecimal monto;
    private String emailComprador;
}