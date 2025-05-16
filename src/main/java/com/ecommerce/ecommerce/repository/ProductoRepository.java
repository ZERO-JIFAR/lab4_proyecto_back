package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
