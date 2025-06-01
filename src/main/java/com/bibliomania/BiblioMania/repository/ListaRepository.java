package com.bibliomania.BiblioMania.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliomania.BiblioMania.model.Lista;

@Repository
public interface ListaRepository  extends JpaRepository<Lista, Long> {
	List<Lista> findByUsuarioId(Long userId);
	int countByUsuarioId(Long userId);

}
