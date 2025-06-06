package com.bibliomania.BiblioMania.dto;

import com.bibliomania.BiblioMania.model.BookCategory;

public class BookCategoryDTO {
    private Long id;
    private Long libroId;
    private Long categoriaId;
    private int prioridad;
    private String libroIsbn;
    
    public BookCategoryDTO() {}

    public BookCategoryDTO(BookCategory bc) {
        this.id = bc.getId();
        this.libroId = bc.getLibro().getId();
        this.categoriaId = bc.getCategoria().getId();
        this.libroIsbn = bc.getLibro().getIsbn();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getLibroId() { return libroId; }
    public void setLibroId(Long bookId) { this.libroId = bookId; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoryId) { this.categoriaId = categoryId; }

    public int getPrioridad() { return prioridad; }
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }

	public String getLibroIsbn() {
		return libroIsbn;
	}

	public void setLibroIsbn(String libroIsbn) {
		this.libroIsbn = libroIsbn;
	}
    
    
    
}
