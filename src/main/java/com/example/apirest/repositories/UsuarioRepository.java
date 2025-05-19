package com.example.apirest.repositories;

import com.example.apirest.entities.Usuario;

public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    //x las dudas:
    Usuario findByEmail(String email);
}
