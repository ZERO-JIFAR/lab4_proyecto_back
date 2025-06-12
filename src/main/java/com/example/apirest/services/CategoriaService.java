package com.example.apirest.services;

import com.example.apirest.entities.Categoria;
import com.example.apirest.entities.Tipo;
import com.example.apirest.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService extends BaseService<Categoria, Long> {

    private final TipoService tipoService;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, TipoService tipoService) {
        super(categoriaRepository);
        this.tipoService = tipoService;
    }

    @Override
    @Transactional
    public Categoria crear(Categoria categoria) throws Exception {
        try {
            // Verificar si la categoría tiene un tipo con ID
            if (categoria.getTipo() != null && categoria.getTipo().getId() != null) {
                // Obtener el tipo completo desde la base de datos
                Tipo tipoCompleto = tipoService.buscarPorId(categoria.getTipo().getId())
                        .orElseThrow(() -> new Exception("Tipo no encontrado con ID: " + categoria.getTipo().getId()));

                // Establecer el tipo completo en la categoría
                categoria.setTipo(tipoCompleto);
            }

            // Guardar la categoría con el tipo completo
            return super.crear(categoria);
        } catch (Exception ex) {
            throw new Exception("Error al crear categoría: " + ex.getMessage());
        }
    }
}