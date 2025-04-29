package com.bibliomania.BiblioMania.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios_grupos")
public class UsuariosGrupos {

    @EmbeddedId
    private UsuariosGruposId id;

    @ManyToOne
    @MapsId("usuarioId")  
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @ManyToOne
    @MapsId("grupoId")
    @JoinColumn(name = "id_grupo", nullable = false)
    private GrupoLectura grupo;

    private LocalDate fechaIngreso; // Fecha opcional

    
    @PrePersist
    public void prePersist() {
        if (fechaIngreso == null) {
            fechaIngreso = LocalDate.now();
        }
    }

    // Getters y Setters
    public UsuariosGruposId getId() { return id; }
    public void setId(UsuariosGruposId id) { this.id = id; }

    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }

    public GrupoLectura getGrupo() { return grupo; }
    public void setGrupo(GrupoLectura grupo) { this.grupo = grupo; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
}
