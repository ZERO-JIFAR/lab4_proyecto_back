package com.example.apirest.repositories;

import com.example.apirest.entities.Producto;
import com.example.apirest.repositories.Base.SoftDeleteRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends SoftDeleteRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByCategoria_Id(Long id);

    List<Producto> findByCategoriaId(Long categoriaId);

    @Query("SELECT p FROM Producto p JOIN TalleProducto tp ON tp.producto.id = p.id " +
            "WHERE tp.talle.id = :talleId AND tp.stock > 0")
    List<Producto> findDisponiblesPorTalle(@Param("talleId") Long talleId);
}
