package com.bibliomania.BiblioMania.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bibliomania.BiblioMania.model.LibroLeido;

public interface LibroLeidoRepository extends JpaRepository<LibroLeido, Long> {
	
    List<LibroLeido> findByUsuarioId(Long userId);
    boolean existsByUsuarioIdAndLibroIsbn(Long userId, String isbn);
    void deleteByUsuarioIdAndLibroIsbn(Long userId, String isbn);
}
