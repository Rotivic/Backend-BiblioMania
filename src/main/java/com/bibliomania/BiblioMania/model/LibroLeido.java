package com.bibliomania.BiblioMania.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LibroLeido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User usuario;

    @ManyToOne
    private Book libro;

    private LocalDateTime fechaLectura;

    @PrePersist
    public void onPrePersist() {
        this.fechaLectura = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Book getLibro() {
        return libro;
    }

    public void setLibro(Book libro) {
        this.libro = libro;
    }

    public LocalDateTime getFechaLectura() {
        return fechaLectura;
    }

    public void setFechaLectura(LocalDateTime fechaLectura) {
        this.fechaLectura = fechaLectura;
    }
}
