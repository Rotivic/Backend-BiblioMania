package com.bibliomania.BiblioMania.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bibliomania.BiblioMania.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByLibroIsbn(String libroId);
	List<Review> findByUsuarioId(Long usuarioId);
	Optional<Review> findByUsuarioIdAndLibroIsbn(Long userId, String isbn);
	@Query("SELECT AVG(r.calificacion) FROM Review r WHERE r.libro.isbn = :isbn")
	Double findAverageCalificacionByLibroIsbn(@Param("isbn") String isbn);

}
