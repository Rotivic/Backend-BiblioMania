package com.bibliomania.BiblioMania.dto;

import com.bibliomania.BiblioMania.model.FavoriteBook;

public class FavoriteBookDTO {
    private Long id;
    private String isbn;
    private String tituloLibro;
    private Long userId;

    public FavoriteBookDTO(FavoriteBook favorite) {
        this.id = favorite.getId();
        this.isbn = favorite.getLibro().getIsbn();
        this.tituloLibro = favorite.getLibro().getTitle();
        this.userId = favorite.getUsuario().getId();
    }


    // Getters y setters...
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookId() {
		return isbn;
	}

	public void setBookId(String bookId) {
		this.isbn = bookId;
	}

	public String getTituloLibro() {
		return tituloLibro;
	}

	public void setTituloLibro(String tituloLibro) {
		this.tituloLibro = tituloLibro;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    
}