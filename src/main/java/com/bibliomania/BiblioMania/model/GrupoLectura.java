package com.bibliomania.BiblioMania.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "grupos_lectura")
public class GrupoLectura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Long idGrupo;

    private String nombre;
    private String descripcion;
 
    @ManyToMany(mappedBy = "grupos", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> usuarios;


    // Getters y Setters
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

    
}
