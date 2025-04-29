package com.bibliomania.BiblioMania.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "grupos_lectura")
public class GrupoLectura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_grupo")  
	private Long id;

    private String nombre;
    private String descripcion;
    private boolean activo;
    
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuariosGrupos> usuarios;


    // Getters y Setters
    
    public Long getidGrupo() {
    	return id;
    }
    
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public boolean getActivo() {
		return activo;
	}
	
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

    
}
