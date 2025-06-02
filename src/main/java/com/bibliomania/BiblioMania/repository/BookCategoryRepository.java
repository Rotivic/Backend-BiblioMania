// BookCategoryRepository.java
package com.bibliomania.BiblioMania.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.BookCategory;
import com.bibliomania.BiblioMania.model.Category;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
	List<BookCategory> findByLibroId(Long bookId);

	List<BookCategory> findByCategoriaId(Long categoryId);

	@Query("""
			    SELECT bc.categoria.id, COUNT(bc.categoria.id)
			    FROM LibroLeido ll
			    JOIN BookCategory bc ON ll.libro.id = bc.libro.id
			    WHERE ll.usuario.id = :userId
			    GROUP BY bc.categoria.id
			    ORDER BY COUNT(bc.categoria.id) DESC
			""")
	List<Object[]> findCategoryUsageByUserId(@Param("userId") Long userId);
	Optional<BookCategory> findByLibroAndCategoria(Book libro, Category categoria);

}
