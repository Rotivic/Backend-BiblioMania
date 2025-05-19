package com.bibliomania.BiblioMania.dto;

import java.time.LocalDateTime;

public class LibroLeidoDTO {

    private Long id;
    private String isbn;
    private String titulo;
    private LocalDateTime fechaLectura;

    public LibroLeidoDTO(Long id, String isbn, String titulo, LocalDateTime fechaLectura) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.fechaLectura = fechaLectura;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getFechaLectura() {
        return fechaLectura;
    }
}
