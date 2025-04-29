package com.bibliomania.BiblioMania.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.PrePersist;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long id;

    private String title;
    private String author;
    private String isbn;
    private String description;
    
    @Column(nullable = false)
    private boolean activo = true;

    @PrePersist
    public void prePersist() {
        if (!activo) activo = true;  // Asegura que siempre sea true al crearse
    }
    
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn
    private List<ListaLibro> listas;
    
    
    
    // Getters y Setters
    
	public String getTitle() {
		return title;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<ListaLibro> getListas() {
		return listas;
	}
	public void setListas(List<ListaLibro> listas) {
		this.listas = listas;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isActivo() {
        return activo;
    }
	
	public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    

    
}
