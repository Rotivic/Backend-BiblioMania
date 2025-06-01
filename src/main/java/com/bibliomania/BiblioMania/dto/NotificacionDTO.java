package com.bibliomania.BiblioMania.dto;

import java.time.LocalDateTime;

import com.bibliomania.BiblioMania.model.Notificacion;

public class NotificacionDTO {
    private Long id;
    private String titulo;
    private String mensaje;
    private boolean leida;
    private LocalDateTime fechaCreacion;

    // Constructor desde entidad
    public NotificacionDTO(Notificacion noti) {
        this.id = noti.getId();
        this.titulo = noti.getTitulo();
        this.mensaje = noti.getMensaje();
        this.leida = noti.isLeida();
        this.fechaCreacion = noti.getFechaCreacion();
    }


    // Getters y setters
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isLeida() {
		return leida;
	}

	public void setLeida(boolean leida) {
		this.leida = leida;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

    
    
}
