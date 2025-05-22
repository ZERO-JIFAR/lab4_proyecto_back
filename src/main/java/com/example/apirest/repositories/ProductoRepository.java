package com.example.apirest.repositories;

import com.example.apirest.entities.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByCategoria_Id(Long id);

    List<Producto> findByCantidadGreaterThanAndCategoria_Id(Integer cantidad, Long id);
}
