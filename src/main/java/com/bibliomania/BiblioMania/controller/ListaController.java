package com.bibliomania.BiblioMania.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bibliomania.BiblioMania.model.Lista;
import com.bibliomania.BiblioMania.service.ListaService;

@RestController
@RequestMapping("/api/listas")
public class ListaController {

    @Autowired
    private ListaService listaService;

    @GetMapping
    public List<Lista> getAllListas() {
        return listaService.getAllListas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lista> getListaById(@PathVariable Long id) {
        Lista lista = listaService.getListaById(id);
        return lista != null ? ResponseEntity.ok(lista) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Lista createLista(@RequestBody Lista lista) {
        return listaService.createLista(lista);
    }
    
    @PutMapping("/{id}")
    public Lista updateLista(@PathVariable Long id, @RequestBody Lista lista) {
		return listaService.updateLista(id, lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
        listaService.deleteLista(id);
        return ResponseEntity.noContent().build();
    }
}