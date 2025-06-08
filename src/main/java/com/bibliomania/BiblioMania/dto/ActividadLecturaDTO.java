package com.bibliomania.BiblioMania.dto;

import java.time.LocalDate;

public class ActividadLecturaDTO {

    private Long id;
    private Long usuarioId;
    private String isbn;
    private String descripcion;
    private Integer minutosInvertidos;
    private LocalDate fecha;

    public ActividadLecturaDTO() {}

    public ActividadLecturaDTO(Long id, Long usuarioId, String isbn, String descripcion, int minutosInvertidos, LocalDate fecha) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.isbn = isbn;
        this.descripcion = descripcion;
        this.minutosInvertidos = minutosInvertidos;
        this.fecha = fecha;
    }


    // Getters y setters...
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getMinutosInvertidos() {
		return minutosInvertidos;
	}

	public void setMinutosInvertidos(int minutosInvertidos) {
		this.minutosInvertidos = minutosInvertidos;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

    
    
}
