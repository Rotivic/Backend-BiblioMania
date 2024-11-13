package com.bibliomania.BiblioMania.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
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
    private List<Review> reseñas;

    @OneToMany(mappedBy = "usuario")
    private List<Lista> listas;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
    		  name = "usuarios_grupos", 
    	        joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"), 
    	        inverseJoinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo") 
    )
    private List<GrupoLectura> grupos;
    
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
    
    
    
}
