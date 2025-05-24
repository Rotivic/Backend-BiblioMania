package com.bibliomania.BiblioMania.dto;

import com.bibliomania.BiblioMania.model.Message;

public class MessageDTO {
    private Long idMessage;
    private String contenido;
    private Long idThread;
    private Long idUsuario; // ID del usuario que envió el mensaje
    private String nombreUsuario; // Nombre del usuario que envió el mensaje

    public MessageDTO(Message message) {
        this.idMessage = message.getIdMessage();
        this.contenido = message.getContenido();
        this.idThread = message.getForumThread().getIdThread();
        this.idUsuario = message.getUsuario().getId();
        this.nombreUsuario = message.getUsuario().getName();
    }

    // Getters y Setters
    public Long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getIdThread() {
        return idThread;
    }

    public void setIdThread(Long idThread) {
        this.idThread = idThread;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
