package com.bibliomania.BiblioMania.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es válido")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
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
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacion> notificaciones;
    
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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

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
