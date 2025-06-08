package com.bibliomania.BiblioMania.dto;

import java.time.LocalDateTime;

import com.bibliomania.BiblioMania.model.Message;

public class MessageDTO {
    private Long idMessage;
    private String contenido;
    private Long idThread;
    private Long idUsuario; // ID del usuario que envió el mensaje
    private String nombreUsuario; // Nombre del usuario que envió el mensaje
    private String profileImageUrl;
    private String chatColor;
    private LocalDateTime fechaEnvio;
    
    public MessageDTO(Message message) {
        this.idMessage = message.getIdMessage();
        this.contenido = message.getContenido();
        this.idThread = message.getForumThread().getIdThread();
        this.idUsuario = message.getUsuario().getId();
        this.nombreUsuario = message.getUsuario().getName();
        this.profileImageUrl = message.getUsuario().getProfileImageUrl();
        this.chatColor = message.getUsuario().getChatColor();
        this.fechaEnvio = message.getFechaEnvio();
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

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getChatColor() {
		return chatColor;
	}

	public void setChatColor(String chatColor) {
		this.chatColor = chatColor;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
    
    
    
}
