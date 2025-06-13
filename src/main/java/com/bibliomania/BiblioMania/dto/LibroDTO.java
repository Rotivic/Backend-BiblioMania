package com.bibliomania.BiblioMania.dto;

public class LibroDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private boolean activo;
    private String portadaUrl;
    private Integer paginas;
    private Integer anioPublicacion;
    private String editorial;
    
    public LibroDTO() {}

	public LibroDTO(String title, String author, String isbn, String description, boolean activo,
                        String portadaUrl, int paginas, int anioPublicacion, String editorial) {
                super();
                
                this.title = title;
                this.author = author;
                this.isbn = isbn;
                this.description = description;
                this.activo = activo;
                this.portadaUrl = portadaUrl;
                this.paginas = paginas;
                this.anioPublicacion = anioPublicacion;
                this.editorial = editorial;
        }    

    public LibroDTO(Long id, String title, String author, String isbn, String description, boolean activo,
			String portadaUrl, int paginas, int anioPublicacion, String editorial) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.description = description;
		this.activo = activo;
		this.portadaUrl = portadaUrl;
		this.paginas = paginas;
		this.anioPublicacion = anioPublicacion;
		this.editorial = editorial;
	}



	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }



	public String getPortadaUrl() {
		return portadaUrl;
	}



	public void setPortadaUrl(String portadaUrl) {
		this.portadaUrl = portadaUrl;
	}



	public Integer getPaginas() {
		return paginas;
	}



	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}



	public Integer getAnioPublicacion() {
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

