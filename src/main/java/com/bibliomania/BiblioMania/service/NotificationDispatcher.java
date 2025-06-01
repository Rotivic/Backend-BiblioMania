package com.bibliomania.BiblioMania.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.bibliomania.BiblioMania.dto.NotificacionDTO;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.UserRepository;

@Component
public class NotificationDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(NotificationDispatcher.class);

    private final NotificacionService notificationService;
    private final UserRepository userRepository;

    public NotificationDispatcher(NotificacionService notificationService, UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    public void dispatchToUser(Long userId, String titulo, String mensaje) {
        if (userId == null || titulo == null || mensaje == null) {
            logger.warn("Parámetros nulos al enviar notificación a un usuario: {}, {}, {}", userId, titulo, mensaje);
            return;
        }

        try {
            notificationService.crearNotificacion(userId, titulo, mensaje);
            logger.info("Notificación enviada a usuario {}: '{}'", userId, titulo);
        } catch (Exception e) {
            logger.error("Error enviando notificación al usuario {}: {}", userId, e.getMessage(), e);
        }
    }

    @Async
    public void dispatchToUsers(List<Long> userIds, String titulo, String mensaje) {
        if (userIds == null || userIds.isEmpty() || titulo == null || mensaje == null) {
            logger.warn("Parámetros inválidos al enviar notificaciones a múltiples usuarios");
            return;
        }

        for (Long userId : userIds) {
            dispatchToUser(userId, titulo, mensaje);
        }
    }

    @Async
    public void dispatchFromDTO(NotificacionDTO dto, List<Long> userIds) {
        if (dto == null || userIds == null || userIds.isEmpty()) {
            logger.warn("DTO o lista de usuarios inválida en dispatchFromDTO");
            return;
        }

        for (Long userId : userIds) {
            dispatchToUser(userId, dto.getTitulo(), dto.getMensaje());
        }
    }

    /**
     * Envía una notificación a todos los administradores del sistema
     */
    public void notificarAdmins(String titulo, String mensaje) {
        List<User> admins = userRepository.findByRol("ADMIN");
        if (admins.isEmpty()) {
            logger.warn("No hay administradores registrados para notificar.");
            return;
        }

        for (User admin : admins) {
            dispatchToUser(admin.getId(), titulo, mensaje);
        }
    }
}
