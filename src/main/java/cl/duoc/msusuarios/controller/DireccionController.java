package cl.duoc.msusuarios.controller;

import cl.duoc.msusuarios.model.Direccion;
import cl.duoc.msusuarios.service.DireccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    @PostMapping
    public ResponseEntity<Direccion> crear(@Valid @RequestBody Direccion direccion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionService.crear(direccion));
    }

    @GetMapping
    public ResponseEntity<List<Direccion>> listar() {
        return ResponseEntity.ok(direccionService.listar());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Direccion>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(direccionService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(direccionService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Direccion> actualizar(@PathVariable Long id, @Valid @RequestBody Direccion direccion) {
        return ResponseEntity.ok(direccionService.actualizar(id, direccion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        direccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}