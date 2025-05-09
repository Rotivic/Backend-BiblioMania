package com.bibliomania.BiblioMania.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bibliomania.BiblioMania.dto.ListaDTO;
import com.bibliomania.BiblioMania.model.Lista;
import com.bibliomania.BiblioMania.service.ListaService;

@RestController
@RequestMapping("/api/listas")
public class ListaController {

    @Autowired
    private ListaService listaService;

    /**
     * Obtiene todas las listas en formato DTO.
     */
    @GetMapping
    public List<ListaDTO> getAllListas() {
        return listaService.getAllListas();
    }

    /**
     * Obtiene una lista por ID en formato DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ListaDTO> getListaById(@PathVariable Long id) {
        return ResponseEntity.ok(listaService.getListaById(id));
    }

    /**
     * Crea una nueva lista.
     */
    @PostMapping
    public ResponseEntity<ListaDTO> createLista(@RequestBody Lista lista) {
        return ResponseEntity.ok(listaService.createLista(lista));
    }

    /**
     * Actualiza una lista existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ListaDTO> updateLista(@PathVariable Long id, @RequestBody Lista lista) {
        return ResponseEntity.ok(listaService.updateLista(id, lista));
    }

    /**
     * Agrega un libro a una lista existente.
     */
    @PutMapping("/{id}/addBook")
    public ResponseEntity<ListaDTO> addBookToLista(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String bookId = payload.get("bookId");
        return ResponseEntity.ok(listaService.addBookToLista(id, bookId));
    }

    /**
     * Eliminar un libro de una lista existente
     */
    @PutMapping("/{id}/removeBook")
    public ResponseEntity<ListaDTO> removeBookFromLista(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload
    ) {
        String bookId = payload.get("bookId");
        return ResponseEntity.ok(listaService.removeBookFromLista(id, bookId));
    }

    
    /**
     * Cambia el estado activo/inactivo de una lista.
     */
    @PutMapping("/{id}/toggleStatus")
    public ResponseEntity<ListaDTO> toggleListaStatus(@PathVariable Long id) {
        return ResponseEntity.ok(listaService.toggleListaStatus(id));
    }

    /**
     * Desactiva una lista.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
        listaService.deleteLista(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<ListaDTO>> obtenerListasPorUsuario(@PathVariable Long userId) {
        List<ListaDTO> listas = listaService.obtenerListasPorUsuario(userId);
        return ResponseEntity.ok(listas);
    }
}
