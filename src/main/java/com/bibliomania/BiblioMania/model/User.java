package com.bibliomania.BiblioMania.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    private String name;
    private String email;
    private String password;
    private String rol;
    
    private String verificacionToken;
    private boolean isVerified = false;
    
    @OneToMany(mappedBy = "usuario")
    @OrderColumn
    private List<Review> reseñas;

    @OneToMany(mappedBy = "usuario")
    @OrderColumn
    private List<Lista> listas;

    @Column(nullable = false)
    private boolean activo = true;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn
    private List<UsuariosGrupos> grupos;
    
    @PrePersist
    public void prePersist() {
        if (activo == false) activo = true;
        if (isVerified == true) isVerified = false;
    }
    
    public User() {}
    
    public User(String name, String email, String password, String rol, boolean isVerified, boolean activo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.isVerified = isVerified;
        this.activo = activo;
    }
    
    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return name; }
    public void setNombre(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
	public String getRol() { return rol; }
	public void setRol(String rol) { this.rol = rol; }
	
	public String getVerificacionToken() {
		return verificacionToken;
	}
	public void setVerificacionToken(String verificacionToken) {
		this.verificacionToken = verificacionToken;
	}
	public boolean isVerified() {
		return isVerified;
	}
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
    
	public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
