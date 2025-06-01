package com.bibliomania.BiblioMania.model;

import java.time.LocalDateTime;

import com.bibliomania.BiblioMania.model.enums.Urgencia;
import com.bibliomania.BiblioMania.model.enums.EstadoReporte;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReporteMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mensaje_id")
    private Message mensaje;

    @ManyToOne
    @JoinColumn(name = "reportado_por_id")
    private User reportadoPor;

    private String motivo;

    @Enumerated(EnumType.STRING)
    private Urgencia urgencia;

    @Enumerated(EnumType.STRING)
    private EstadoReporte estado = EstadoReporte.PENDIENTE;
    
    private LocalDateTime fecha = LocalDateTime.now();

    // Getters y setters
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Message getMensaje() {
		return mensaje;
	}

	public void setMensaje(Message mensaje) {
		this.mensaje = mensaje;
	}

	public User getReportadoPor() {
		return reportadoPor;
	}

	public void setReportadoPor(User reportadoPor) {
		this.reportadoPor = reportadoPor;
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

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public EstadoReporte getEstado() {
	    return estado;
	}

	public void setEstado(EstadoReporte estado) {
	    this.estado = estado;
	}
    
    
}

