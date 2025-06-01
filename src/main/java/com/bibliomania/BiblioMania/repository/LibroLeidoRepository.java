package com.bibliomania.BiblioMania.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bibliomania.BiblioMania.model.LibroLeido;

public interface LibroLeidoRepository extends JpaRepository<LibroLeido, Long> {

	List<LibroLeido> findByUsuarioId(Long userId);

	boolean existsByUsuarioIdAndLibroIsbn(Long userId, String isbn);

	void deleteByUsuarioIdAndLibroIsbn(Long userId, String isbn);

	@Query("""
			    SELECT FUNCTION('MONTH', ll.fechaLectura), COUNT(ll)
			    FROM LibroLeido ll
			    WHERE ll.usuario.id = :userId
			    GROUP BY FUNCTION('MONTH', ll.fechaLectura)
			    ORDER BY FUNCTION('MONTH', ll.fechaLectura)
			""")
	List<Object[]> countBooksReadPerMonth(@Param("userId") Long userId);

	int countByUsuarioId(Long userId);
}
