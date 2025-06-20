package com.bibliomania.BiblioMania.controller;

import com.bibliomania.BiblioMania.dto.GrupoLecturaDTO;
import com.bibliomania.BiblioMania.model.GrupoLectura;
import com.bibliomania.BiblioMania.service.GrupoLecturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grupos")
public class GrupoLecturaController {

    @Autowired
    private GrupoLecturaService grupoLecturaService;

    @GetMapping
    public List<GrupoLecturaDTO> getAllGrupos() {
    	return grupoLecturaService.getAllGrupos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoLectura> getGrupoById(@PathVariable Long id) {
        GrupoLectura grupo = grupoLecturaService.getGrupoById(id);
        return grupo != null ? ResponseEntity.ok(grupo) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public GrupoLectura createGrupo(@RequestBody GrupoLectura grupoLectura) {
        return grupoLecturaService.createGrupo(grupoLectura);
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<?> toggleGrupoActivo(@PathVariable Long id) {
        boolean updated = grupoLecturaService.toggleGrupoActivo(id);
        return updated ? ResponseEntity.ok("Estado del grupo actualizado") : ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/descripcion")
    public ResponseEntity<?> updateDescripcion(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String nuevaDescripcion = request.get("descripcion");
        boolean updated = grupoLecturaService.updateDescripcionGrupo(id, nuevaDescripcion);
        return updated ? ResponseEntity.ok("Descripción actualizada") : ResponseEntity.badRequest().body("No se pudo actualizar la descripción (¿grupo inactivo?)");
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<?> joinGroup(@PathVariable Long groupId, @RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        boolean success = grupoLecturaService.addUserToGroup(userId, groupId);
        return success ? ResponseEntity.ok("Usuario añadido al grupo") : ResponseEntity.badRequest().body("Error al unirse al grupo");
    }

    @PostMapping("/{groupId}/leave")
    public ResponseEntity<?> leaveGroup(@PathVariable Long groupId, @RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        boolean success = grupoLecturaService.removeUserFromGroup(userId, groupId);
        return success ? ResponseEntity.ok("Usuario eliminado del grupo") : ResponseEntity.badRequest().body("Error al salir del grupo");
    }
    
    @GetMapping("/{grupoId}/isMember/{userId}")
    public ResponseEntity<Boolean> isUserMember(@PathVariable Long grupoId, @PathVariable Long userId) {
        boolean isMember = grupoLecturaService.isUserMember(userId, grupoId);
        return ResponseEntity.ok(isMember);
    }

   @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<GrupoLecturaDTO>> getGruposPorUsuario(@PathVariable Long userId) {
        List<GrupoLecturaDTO> grupos = grupoLecturaService.getGruposPorUsuario(userId);
        return ResponseEntity.ok(grupos);
    }

}
