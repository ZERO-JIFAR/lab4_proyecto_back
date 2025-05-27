package com.example.apirest.repositories;

import com.example.apirest.entities.Usuario;
import com.example.apirest.repositories.Base.SoftDeleteRepository;

public interface UsuarioRepository extends SoftDeleteRepository<Usuario, Long> {

    //x las dudas:
    Usuario findByEmail(String email);
}
