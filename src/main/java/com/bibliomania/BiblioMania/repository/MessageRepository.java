package com.bibliomania.BiblioMania.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bibliomania.BiblioMania.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByForumThread_Id(Long idThread);
    List<Message> findByForumThread_IdOrderByFechaEnvioAsc(Long threadId);
    
    @Query("SELECT DISTINCT m.usuario.id FROM Message m WHERE m.forumThread.id = :threadId")
    List<Long> findDistinctUserIdsByThread(@Param("threadId") Long threadId);

}