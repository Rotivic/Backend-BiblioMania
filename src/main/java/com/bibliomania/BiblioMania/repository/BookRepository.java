package com.bibliomania.BiblioMania.repository;

import com.bibliomania.BiblioMania.model.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
	Optional<Book> findByIsbn(String isbn);
	List<Book> findByActivoTrue();
    // Puedes agregar métodos personalizados aquí si es necesario
}
