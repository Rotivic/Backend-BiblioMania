// BookCategoryRepository.java
package com.bibliomania.BiblioMania.repository;

import com.bibliomania.BiblioMania.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
    List<BookCategory> findByLibroId(Long bookId);
    List<BookCategory> findByCategoriaId(Long categoryId);
}
