package com.example.apirest.repositories;

import com.example.apirest.entities.TipoTalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTalleRepository extends JpaRepository<TipoTalle, Long> {
}