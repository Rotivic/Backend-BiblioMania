package com.bibliomania.BiblioMania.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_review")
	    private Long id;
	    @Lob
	    private String comentario;
	    private int calificacion;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "id_usuario", nullable = false)
	    private User usuario;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "id_libro", nullable = false)
	    private Book libro;

    // Getters y Setters
  
	    
	    
	public String getContenido() {
		return comentario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public void setContenido(String comentario) {
		this.comentario = comentario;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
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


    
    
}