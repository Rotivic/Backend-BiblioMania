package com.bibliomania.BiblioMania.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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
    @Column(length = 20, unique = true)
    private String isbn;
    @Lob
    private String description;
    
    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "portada_url")
    private String portadaUrl;

    @Column(name = "paginas")
    private int paginas;

    @Column(name = "anio_publicacion")
    private int anioPublicacion;

    @Column(name = "editorial")
    private String editorial;

    
    @PrePersist
    public void prePersist() {
        if (!activo) activo = true;  
    }
    
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaLibro> listas;
    
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookCategory> categorias;

    
    
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
	public String getPortadaUrl() {
		return portadaUrl;
	}
	public void setPortadaUrl(String portadaUrl) {
		this.portadaUrl = portadaUrl;
	}
	public int getPaginas() {
		return paginas;
	}
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}
	public int getAnioPublicacion() {
		return anioPublicacion;
	}
	public void setAnioPublicacion(int anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
    
    
    
    
}
