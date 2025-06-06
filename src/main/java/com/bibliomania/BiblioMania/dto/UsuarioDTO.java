package com.bibliomania.BiblioMania.dto;

import java.time.LocalDateTime;


public class UsuarioDTO {
	 private Long id;
	    private String name;
	    private String email;
	    private String profileImageUrl;
	    private String chatColor;
	    private String bio;
	    private String idiomaPreferido;
	    private LocalDateTime fechaRegistro;
	    private String rol;
	    private Boolean activo;
	    
    public UsuarioDTO() {}

	public UsuarioDTO(Long id, String name, String email, String profileImageUrl, String chatColor, String bio,
			String idiomaPreferido, LocalDateTime fechaRegistro, String rol, Boolean activo) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.chatColor = chatColor;
		this.bio = bio;
		this.idiomaPreferido = idiomaPreferido;
		this.fechaRegistro = fechaRegistro;
		this.rol = rol;
		this.activo = activo;
	}



	public boolean isActivo() {
        return activo;
    }

	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void setName(String name) { this.name = name; }

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getChatColor() {
		return chatColor;
	}

	public void setChatColor(String chatColor) {
		this.chatColor = chatColor;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getIdiomaPreferido() {
		return idiomaPreferido;
	}

	public void setIdiomaPreferido(String idiomaPreferido) {
		this.idiomaPreferido = idiomaPreferido;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getName() {
		return name;
	}
    
	
    
    
}
