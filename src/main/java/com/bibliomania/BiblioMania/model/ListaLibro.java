package com.bibliomania.BiblioMania.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_libro")
public class ListaLibro {

    @EmbeddedId
    private ListaLibroId id;

    @ManyToOne
    @MapsId("listaId")
    @JoinColumn(name = "lista_id", nullable = false)
    @JsonBackReference
    private Lista lista;

    @ManyToOne
    @MapsId("libroId")
    @JoinColumn(name = "libro_id", nullable = false)
    private Book libro;

    public ListaLibro() {}

    public ListaLibro(Lista lista, Book libro) {
        this.id = new ListaLibroId(lista.getId(), libro.getId());
        this.lista = lista;
        this.libro = libro;
    }

    public ListaLibroId getId() {
        return id;
    }

    public void setId(ListaLibroId id) {
        this.id = id;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public Book getLibro() {
        return libro;
    }

    public void setLibro(Book libro) {
        this.libro = libro;
    }
}
