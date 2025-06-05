package com.bibliomania.BiblioMania.controller;

import com.bibliomania.BiblioMania.dto.ActividadLecturaDTO;
import com.bibliomania.BiblioMania.service.ActividadLecturaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/actividades")
public class ActividadLecturaController {

    @Autowired
    private ActividadLecturaService service;

    @PostMapping
    public ResponseEntity<ActividadLecturaDTO> crearActividad(@RequestBody ActividadLecturaDTO dto) {
        return ResponseEntity.ok(service.crearActividad(dto));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ActividadLecturaDTO>> obtenerActividadesUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.obtenerPorUsuario(usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
