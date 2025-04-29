package com.bibliomania.BiblioMania.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliomania.BiblioMania.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByForumThread_Id(Long idThread);
    List<Message> findByForumThread_IdOrderByFechaEnvioAsc(Long threadId);
}