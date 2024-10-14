package com.bibliomania.BiblioMania.repository;

import com.bibliomania.BiblioMania.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
