package com.bibliomania.BiblioMania.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.model.GrupoLectura;
import com.bibliomania.BiblioMania.service.GrupoLecturaService;

@RestController
@RequestMapping("/api/grupos")
public class GrupoLecturaController {

    @Autowired
    private GrupoLecturaService grupoLecturaService;

    @GetMapping
    public List<GrupoLectura> getAllGrupos() {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupo(@PathVariable Long id) {
        grupoLecturaService.deleteGrupo(id);
        return ResponseEntity.noContent().build();
    }
}