package com.bibliomania.BiblioMania.service;

import java.util.List;
import java.util.Optional;
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
        Optional.ofNullable(reporte.getMensaje())
        .ifPresent(m -> dto.setMensajeId(m.getIdMessage()));// Asegúrate que sea getId() o getIdMessage()
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

        ReporteMensaje reporte = new ReporteMensaje();
        reporte.setReportadoPor(usuario);
        reporte.setMotivo(dto.getMotivo());
        reporte.setUrgencia(dto.getUrgencia());
        reporte.setEstado(EstadoReporte.PENDIENTE);

        // Solo asignar mensaje si se proporciona un ID
        if (dto.getMensajeId() != null) {
            Message mensaje = messageRepository.findById(dto.getMensajeId())
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
            reporte.setMensaje(mensaje);
        }

        ReporteMensaje guardado = reporteMensajeRepository.save(reporte);

        // Notificar admins
        String titulo = "📢 Nuevo reporte " +
            (dto.getMensajeId() != null ? "de mensaje" : "general") +
            " (" + dto.getUrgencia().name() + ")";
        String contenido = (dto.getMensajeId() != null
            ? "Mensaje reportado por usuario " + usuario.getName()
            : "Reporte general enviado por usuario " + usuario.getName()) +
            ": " + dto.getMotivo();

        notificationDispatcher.notificarAdmins(titulo, contenido);

        return new ReporteMensajeDTO(
            guardado.getId(),
            guardado.getMensaje() != null ? guardado.getMensaje().getIdMessage() : null,
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
                r.getMensaje() != null ? r.getMensaje().getIdMessage() : null, 
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
            r.getMensaje() != null ? r.getMensaje().getIdMessage() : null, // 🔁 prevenir null
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

        // 🔔 Notificar al usuario que hizo el reporte
        String titulo = "📌 Estado de tu reporte actualizado";
        String contenido = "Tu reporte " +
            (actualizado.getMensaje() != null ? "sobre un mensaje" : "general") +
            " ha sido actualizado a: " + nuevoEstado.name();

        notificationDispatcher.dispatchToUser(actualizado.getReportadoPor().getId(), titulo, contenido);
        
        return new ReporteMensajeDTO(
            actualizado.getId(),
            actualizado.getMensaje() != null ? actualizado.getMensaje().getIdMessage() : null, // 🔁 prevenir null
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
