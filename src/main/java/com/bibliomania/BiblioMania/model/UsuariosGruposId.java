package com.bibliomania.BiblioMania.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class UsuariosGruposId implements Serializable {

	    private Long usuarioId;

	    private Long grupoId;

    // Constructores
    public UsuariosGruposId() {}

    public UsuariosGruposId(Long usuarioId, Long grupoId) {
        this.usuarioId = usuarioId;
        this.grupoId = grupoId;
    }

    // Getters y Setters
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getGrupoId() { return grupoId; }
    public void setGrupoId(Long grupoId) { this.grupoId = grupoId; }

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuariosGruposId that = (UsuariosGruposId) o;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(grupoId, that.grupoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, grupoId);
    }
}
