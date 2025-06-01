package com.bibliomania.BiblioMania.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.dto.ReporteMensajeDTO;
import com.bibliomania.BiblioMania.model.Message;
import com.bibliomania.BiblioMania.model.ReporteMensaje;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.model.enums.EstadoReporte;
import com.bibliomania.BiblioMania.repository.MessageRepository;
import com.bibliomania.BiblioMania.repository.ReporteMensajeRepository;
import com.bibliomania.BiblioMania.repository.UserRepository;

@Service
public class ReporteMensajeService {

    private final ReporteMensajeRepository reporteMensajeRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final NotificationDispatcher notificationDispatcher;

    
    private ReporteMensajeDTO convertirADTO(ReporteMensaje reporte) {
        ReporteMensajeDTO dto = new ReporteMensajeDTO();
        dto.setMensajeId(reporte.getId());
        dto.setMensajeId(reporte.getMensaje().getIdMessage()); // Asegúrate que sea getId() o getIdMessage()
        dto.setUsuarioId(reporte.getReportadoPor().getId());
        dto.setMotivo(reporte.getMotivo());
        dto.setUrgencia(reporte.getUrgencia()); // Si es Enum
        dto.setEstado(reporte.getEstado());     // Si es Enum
        dto.setFecha(reporte.getFecha());
        return dto;
    }

    
    public ReporteMensajeService(
        ReporteMensajeRepository reporteMensajeRepository,
        MessageRepository messageRepository,
        UserRepository userRepository,
        NotificationDispatcher notificationDispatcher
    ) {
        this.reporteMensajeRepository = reporteMensajeRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.notificationDispatcher = notificationDispatcher;
    }

    public ReporteMensajeDTO crearReporte(ReporteMensajeDTO dto) {
        User usuario = userRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Message mensaje = messageRepository.findById(dto.getMensajeId())
            .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));

        ReporteMensaje reporte = new ReporteMensaje();
        reporte.setReportadoPor(usuario);
        reporte.setMensaje(mensaje);
        reporte.setMotivo(dto.getMotivo());
        reporte.setUrgencia(dto.getUrgencia());
        reporte.setEstado(EstadoReporte.PENDIENTE);

        ReporteMensaje guardado = reporteMensajeRepository.save(reporte);

        // Notificar admins
        String titulo = "📢 Nuevo reporte de mensaje (" + dto.getUrgencia().name() + ")";
        String contenido = "Mensaje reportado por usuario " + usuario.getName() + ": " + dto.getMotivo();
        notificationDispatcher.notificarAdmins(titulo, contenido);

        return new ReporteMensajeDTO(
            guardado.getId(),
            guardado.getMensaje().getIdMessage(),
            guardado.getReportadoPor().getId(),
            guardado.getMotivo(),
            guardado.getUrgencia(),
            guardado.getEstado(),
            guardado.getFecha()
        );
    }
    
 // Obtener todos los reportes ordenados por fecha desc
    public List<ReporteMensajeDTO> obtenerTodos() {
        return reporteMensajeRepository.findAllByOrderByFechaDesc()
            .stream()
            .map(r -> new ReporteMensajeDTO(
                r.getId(),
                r.getMensaje().getIdMessage(),
                r.getReportadoPor().getId(),
                r.getMotivo(),
                r.getUrgencia(),
                r.getEstado(),
                r.getFecha()
            ))
            .collect(Collectors.toList());
    }
    
 // Obtener un reporte específico
    public ReporteMensajeDTO obtenerPorId(Long id) {
        ReporteMensaje r = reporteMensajeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        return new ReporteMensajeDTO(
            r.getId(),
            r.getMensaje().getIdMessage(),
            r.getReportadoPor().getId(),
            r.getMotivo(),
            r.getUrgencia(),
            r.getEstado(),
            r.getFecha()
        );
    }
    
 // Actualizar el estado de un reporte
    public ReporteMensajeDTO actualizarEstado(Long id, EstadoReporte nuevoEstado) {
        ReporteMensaje r = reporteMensajeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        r.setEstado(nuevoEstado);
        ReporteMensaje actualizado = reporteMensajeRepository.save(r);

        return new ReporteMensajeDTO(
            actualizado.getId(),
            actualizado.getMensaje().getIdMessage(),
            actualizado.getReportadoPor().getId(),
            actualizado.getMotivo(),
            actualizado.getUrgencia(),
            actualizado.getEstado(),
            actualizado.getFecha()
        );
    }
    
    public List<ReporteMensajeDTO> obtenerPorUsuario(Long usuarioId) {
        List<ReporteMensaje> reportes = reporteMensajeRepository.findByUsuarioReportante(usuarioId);
        return reportes.stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
}
