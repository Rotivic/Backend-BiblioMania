package com.bibliomania.BiblioMania.dto;


import java.util.List;

public class ListaDTO {
    private Long id;
    private String nombre;
    private boolean activo;
    private UsuarioDTO usuario;
    private List<LibroDTO> libros;

    public ListaDTO() {}

    public ListaDTO(Long id, String nombre, boolean activo, UsuarioDTO usuario, List<LibroDTO> libros) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
        this.usuario = usuario;
        this.libros = libros;
    }
	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public UsuarioDTO getUsuario() { return usuario; }
    public void setUsuario(UsuarioDTO usuario) { this.usuario = usuario; }

    public List<LibroDTO> getLibros() { return libros; }
    public void setLibros(List<LibroDTO> libros) { this.libros = libros; }
}
