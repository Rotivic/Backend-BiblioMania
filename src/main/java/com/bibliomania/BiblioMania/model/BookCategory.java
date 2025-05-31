package com.bibliomania.BiblioMania.model;

import jakarta.persistence.*;

@Entity
@Table(name = "book_category")
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Book libro;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Category categoria;


    // Getters y setters
    public Long getId() {
        return id;
    }

    public Book getLibro() {
        return libro;
    }

    public void setLibro(Book libro) {
        this.libro = libro;
    }

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

 
}
