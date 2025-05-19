package com.bibliomania.BiblioMania.controller;

import com.bibliomania.BiblioMania.dto.LibroLeidoDTO;
import com.bibliomania.BiblioMania.model.LibroLeido;
import com.bibliomania.BiblioMania.service.LibroLeidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros-leidos")
public class LibroLeidoController {

    private final LibroLeidoService libroLeidoService;

    public LibroLeidoController(LibroLeidoService service) {
        this.libroLeidoService = service;
    }

    @PostMapping("/usuario/{userId}/isbn/{isbn}")
    public ResponseEntity<LibroLeidoDTO> marcarComoLeido(@PathVariable Long userId, @PathVariable String isbn) {
        LibroLeido leido = libroLeidoService.marcarComoLeido(userId, isbn);
        LibroLeidoDTO dto = new LibroLeidoDTO(leido.getId(), leido.getLibro().getIsbn(), leido.getLibro().getTitle(), leido.getFechaLectura());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<LibroLeidoDTO>> obtenerLibrosLeidos(@PathVariable Long userId) {
        return ResponseEntity.ok(libroLeidoService.obtenerLibrosLeidos(userId));
    }
}
