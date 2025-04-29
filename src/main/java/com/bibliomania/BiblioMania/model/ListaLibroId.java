package com.bibliomania.BiblioMania.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ListaLibroId implements Serializable {
    private Long listaId;
    private Long libroId;

    public ListaLibroId() {}

    public ListaLibroId(Long listaId, Long libroId) {
        this.listaId = listaId;
        this.libroId = libroId;
    }

    public Long getListaId() {
        return listaId;
    }

    public void setListaId(Long listaId) {
        this.listaId = listaId;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaLibroId that = (ListaLibroId) o;
        return Objects.equals(listaId, that.listaId) &&
               Objects.equals(libroId, that.libroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listaId, libroId);
    }
}
