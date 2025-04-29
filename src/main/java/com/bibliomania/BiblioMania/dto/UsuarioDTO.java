package com.bibliomania.BiblioMania.dto;

public class UsuarioDTO {
    private Long id;
    private String email;
    private String nombre;

    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String email, String nombre) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
