package com.bibliomania.BiblioMania.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliomania.BiblioMania.model.ForumThread;

public interface ForumThreadRepository  extends JpaRepository<ForumThread, Long> {
    List<ForumThread> findByGrupoId(Long id);
}