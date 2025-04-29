package com.bibliomania.BiblioMania.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bibliomania.BiblioMania.model.ForumThread;

public class ForumThreadDTO {
    private Long idThread;
    private String titulo;
    private Long idGrupo;
    private List<MessageDTO> messages; 

    public ForumThreadDTO(ForumThread forumThread) {
        this.idThread = forumThread.getIdThread();
        this.titulo = forumThread.getTitulo();
        this.idGrupo = forumThread.getGrupo().getidGrupo();
        this.messages = forumThread.getMessages() != null ? 
            forumThread.getMessages().stream().map(MessageDTO::new).collect(Collectors.toList()) : 
            null;
    }

    // Getters y Setters
    public Long getIdThread() {
        return idThread;
    }

    public void setIdThread(Long idThread) {
        this.idThread = idThread;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
