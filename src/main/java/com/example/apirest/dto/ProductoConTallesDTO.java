package com.example.apirest.dto;

import com.example.apirest.entities.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoConTallesDTO {
    private Producto producto;
    private Map<Long, Integer> tallesConStock; // Map<TalleId, Stock>
}