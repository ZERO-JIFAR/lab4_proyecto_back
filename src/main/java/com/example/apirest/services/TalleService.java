package com.example.apirest.services;

import com.example.apirest.entities.Talle;
import com.example.apirest.repositories.TalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalleService extends BaseService<Talle, Long>{

    private final TalleRepository talleRepository;

    public TalleService(TalleRepository talleRepository) {
        super(talleRepository);
        this.talleRepository = talleRepository;
    }

    public List<Talle> obtenerPorId(Long Id){
        return talleRepository.findById(Id).map(List::of).orElse(List.of());
    }

    public List<Talle> obtenerPorTipoTalle(Long tipoTalleId){
        return talleRepository.findByTipoTalle(tipoTalleId);
    }
}
