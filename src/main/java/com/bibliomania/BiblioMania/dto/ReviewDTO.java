package com.bibliomania.BiblioMania.dto;

public class ReviewDTO {

	private Long id;
    private String comentario;
    private int calificacion;
    private UsuarioDTO usuario;
    private LibroDTO libro;
    
    public ReviewDTO() {
		super();
	}
    
	public ReviewDTO(Long id, String comentario, int calificacion, UsuarioDTO usuario, LibroDTO libro) {
		super();
		this.id = id;
		this.comentario = comentario;
		this.calificacion = calificacion;
		this.usuario = usuario;
		this.libro = libro;
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

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public LibroDTO getLibro() {
		return libro;
	}

	public void setLibro(LibroDTO libro) {
		this.libro = libro;
	}
	
    
    
}
