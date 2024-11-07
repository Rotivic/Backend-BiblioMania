package com.bibliomania.BiblioMania.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/crear")
    public ResponseEntity<Lista> crearLista(@RequestBody Lista lista) {
        Lista nuevaLista = listaService.crearLista(lista);
        return new ResponseEntity<>(nuevaLista, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Lista>> obtenerListas() {
        return new ResponseEntity<>(listaService.obtenerListas(), HttpStatus.OK);
    }
}