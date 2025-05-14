package com.bibliomania.BiblioMania.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bibliomania.BiblioMania.model.FavoriteBook;

import jakarta.transaction.Transactional;

@Repository
public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, Long> {
    List<FavoriteBook> findByUsuarioId(Long userId);

    Optional<FavoriteBook> findByUsuarioIdAndLibroIsbn(Long userId, String isbn);

    @Modifying
    @Transactional
    @Query("DELETE FROM FavoriteBook f WHERE f.usuario.id = :userId AND f.libro.isbn = :isbn")
    void deleteByUserIdAndBookIsbn(@Param("userId") Long userId, @Param("isbn") String isbn);
}
