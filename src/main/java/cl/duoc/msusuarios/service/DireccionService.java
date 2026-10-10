package cl.duoc.msusuarios.service;

import cl.duoc.msusuarios.exception.RecursoNoEncontradoException;
import cl.duoc.msusuarios.model.Direccion;
import cl.duoc.msusuarios.model.Usuario;
import cl.duoc.msusuarios.repository.DireccionRepository;
import cl.duoc.msusuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionService {

    private final DireccionRepository direccionRepository;
    private final UsuarioRepository usuarioRepository;

    public DireccionService(DireccionRepository direccionRepository, UsuarioRepository usuarioRepository) {
        this.direccionRepository = direccionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario obtenerUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new RecursoNoEncontradoException("Debe indicar el id del usuario");
        }
        return usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id " + usuario.getId()));
    }

    public Direccion crear(Direccion direccion) {
        direccion.setUsuario(obtenerUsuario(direccion.getUsuario()));
        return direccionRepository.save(direccion);
    }

    public List<Direccion> listar() {
        return direccionRepository.findAll();
    }

    public List<Direccion> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con id " + usuarioId);
        }
        return direccionRepository.findByUsuarioId(usuarioId);
    }

    public Direccion buscarPorId(Long id) {
        return direccionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Direccion no encontrada con id " + id));
    }

    public Direccion actualizar(Long id, Direccion datos) {
        Direccion existente = buscarPorId(id);
        existente.setCalle(datos.getCalle());
        existente.setNumero(datos.getNumero());
        existente.setComuna(datos.getComuna());
        existente.setCiudad(datos.getCiudad());
        existente.setUsuario(obtenerUsuario(datos.getUsuario()));
        return direccionRepository.save(existente);
    }

    public void eliminar(Long id) {
        Direccion existente = buscarPorId(id);
        direccionRepository.delete(existente);
    }
}