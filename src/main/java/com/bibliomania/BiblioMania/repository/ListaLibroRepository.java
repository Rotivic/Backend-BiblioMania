package com.bibliomania.BiblioMania.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.Lista;
import com.bibliomania.BiblioMania.model.ListaLibro;
import com.bibliomania.BiblioMania.model.ListaLibroId;

@Repository
public interface ListaLibroRepository extends JpaRepository<ListaLibro, ListaLibroId> {
    // Puedes añadir métodos personalizados si los necesitas en el futuro
	boolean existsByListaAndLibro(Lista lista, Book libro);
	Optional<ListaLibro> findById(ListaLibroId id);
	void delete(ListaLibro entity);
}