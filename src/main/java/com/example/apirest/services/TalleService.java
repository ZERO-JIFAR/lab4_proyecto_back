package com.example.apirest.services;

import com.example.apirest.entities.Talle;
import com.example.apirest.repositories.TalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalleService extends BaseService<Talle, Long>{

    public TalleService(TalleRepository talleRepository) {
        super(talleRepository);
    }

    public List<Talle> obtenerPorId(Long Id) throws Exception {
        return buscarPorId(Id).map(List::of).orElse(List.of());
    }

    public List<Talle> obtenerPorTipoTalle(Long tipoTalleId){
        return ((TalleRepository) baseRepository).findByTipoTalle_Id(tipoTalleId);
    }
}
