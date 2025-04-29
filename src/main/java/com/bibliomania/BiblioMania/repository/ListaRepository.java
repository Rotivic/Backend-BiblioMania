package com.bibliomania.BiblioMania.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliomania.BiblioMania.model.Lista;

@Repository
public interface ListaRepository  extends JpaRepository<Lista, Long> {
}
