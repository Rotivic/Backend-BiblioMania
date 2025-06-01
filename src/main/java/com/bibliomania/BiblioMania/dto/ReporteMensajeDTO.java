package com.bibliomania.BiblioMania.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.bibliomania.BiblioMania.model.enums.EstadoReporte;
import com.bibliomania.BiblioMania.model.enums.Urgencia;

public class ReporteMensajeDTO {
	private Long id;
    private Long mensajeId;
    private Long usuarioId;
    private String motivo;
    private LocalDateTime fecha;
    private Urgencia urgencia;
    private EstadoReporte estado;    
    
    public ReporteMensajeDTO() {
		super();
	}
    
	public ReporteMensajeDTO(Long id, Long mensajeId, Long usuarioId, String motivo, Urgencia urgencia, EstadoReporte estado, LocalDateTime fecha) {
		super();
		this.id = id;
		this.mensajeId = mensajeId;
		this.usuarioId = usuarioId;
		this.motivo = motivo;
		this.urgencia = urgencia;
		this.estado = estado;
		this.fecha = fecha;
	}
	
	
	// Getters y setters
  
    
	public Long getMensajeId() {
		return mensajeId;
	}
	public void setMensajeId(Long mensajeId) {
		this.mensajeId = mensajeId;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Urgencia getUrgencia() {
		return urgencia;
	}
	public void setUrgencia(Urgencia urgencia) {
		this.urgencia = urgencia;
	}
    
	public EstadoReporte getEstado() {
	    return estado;
	}

	public void setEstado(EstadoReporte estado) {
	    this.estado = estado;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime localDateTime) {
		this.fecha = localDateTime;
	}
 
	
	
}
