package cl.duoc.msusuarios.repository;

import cl.duoc.msusuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}