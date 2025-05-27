package com.example.apirest.repositories;

import com.example.apirest.entities.Talle;
import com.example.apirest.repositories.Base.SoftDeleteRepository;

import java.util.List;

public interface TalleRepository extends SoftDeleteRepository<Talle, Long> {
    List<Talle> findByTipoTalle_Id (Long tipoTalleId);
}
