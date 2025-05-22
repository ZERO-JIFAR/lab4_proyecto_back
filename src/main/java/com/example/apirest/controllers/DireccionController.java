package com.example.apirest.controllers;

import com.example.apirest.entities.Direccion;
import com.example.apirest.services.DireccionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController extends BaseController<Direccion, Long> {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        super(direccionService);
        this.direccionService = direccionService;
    }

    @Override
    protected Map<String, Object> convertToMap(Direccion entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", entity.getId());
        map.put("calle", entity.getCalle());
        map.put("numero", entity.getNumero());
        map.put("ciudad", entity.getCiudad());
        map.put("provincia", entity.getProvincia());
        map.put("codigoPostal", entity.getCodigoPostal());
        return map;
    }

    @Override
    protected Direccion convertToEntity(Map<String, Object> entityMap) {
        Direccion direccion = new Direccion();
        if (entityMap.containsKey("id")) {
            direccion.setId(Long.valueOf(entityMap.get("id").toString()));
        }
        if (entityMap.containsKey("calle")) {
            direccion.setCalle((String) entityMap.get("calle"));
        }
        if (entityMap.containsKey("numero")) {
            direccion.setNumero((String) entityMap.get("numero"));
        }
        if (entityMap.containsKey("ciudad")) {
            direccion.setCiudad((String) entityMap.get("ciudad"));
        }
        if (entityMap.containsKey("provincia")) {
            direccion.setProvincia((String) entityMap.get("provincia"));
        }
        if (entityMap.containsKey("codigoPostal")) {
            direccion.setCodigoPostal((String) entityMap.get("codigoPostal"));
        }
        return direccion;
    }
}
