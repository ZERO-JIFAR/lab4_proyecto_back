package com.example.apirest.services;

import com.example.apirest.entities.Usuario;
import com.example.apirest.entities.enums.Rol;
import com.example.apirest.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends BaseService<Usuario, Long> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
        this.usuarioRepository = usuarioRepository;
    }

    // Puedes métodos específicos del Usuario aquí
    public Usuario buscarPorEmail(String email) throws Exception {
        try {
            return usuarioRepository.findByEmail(email);
        } catch (Exception ex) {
            throw new Exception("Error al buscar usuario por email: " + ex.getMessage());
        }
    }

    /**
     * Promueve un usuario a rol de administrador
     * @param userId ID del usuario a promover
     * @return Usuario actualizado
     * @throws Exception Si ocurre un error o el usuario no existe
     */
    public Usuario promoverAAdmin(Long userId) throws Exception {
        try {
            Usuario usuario = baseRepository.findById(userId)
                .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + userId));

            usuario.setRol(Rol.ADMIN);
            return baseRepository.save(usuario);
        } catch (Exception ex) {
            throw new Exception("Error al promover usuario a admin: " + ex.getMessage());
        }
    }
}
