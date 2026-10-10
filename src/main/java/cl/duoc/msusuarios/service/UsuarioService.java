package cl.duoc.msusuarios.service;

import cl.duoc.msusuarios.exception.RecursoNoEncontradoException;
import cl.duoc.msusuarios.model.Usuario;
import cl.duoc.msusuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crear(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id " + id));
    }

    public Usuario actualizar(Long id, Usuario datos) {
        Usuario existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setEmail(datos.getEmail());
        existente.setTelefono(datos.getTelefono());
        return usuarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        Usuario existente = buscarPorId(id);
        usuarioRepository.delete(existente);
    }
}
