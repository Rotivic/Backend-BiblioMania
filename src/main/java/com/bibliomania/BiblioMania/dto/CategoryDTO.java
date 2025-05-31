package com.bibliomania.BiblioMania.dto;

import com.bibliomania.BiblioMania.model.Category;

public class CategoryDTO {
    private Long id;
    private String nombre;

    public CategoryDTO() {}

    public CategoryDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public CategoryDTO(Category categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
    }

    public Category toEntity() {
    	Category categoria = new Category();
        categoria.setId(this.id); 
        categoria.setNombre(this.nombre);
        return categoria;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
