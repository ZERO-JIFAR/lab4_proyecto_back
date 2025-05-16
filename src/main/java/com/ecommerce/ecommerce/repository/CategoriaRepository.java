package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
