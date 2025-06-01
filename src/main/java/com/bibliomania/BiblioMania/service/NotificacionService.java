package com.bibliomania.BiblioMania.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.dto.NotificacionDTO;
import com.bibliomania.BiblioMania.model.Notificacion;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.MessageRepository;
import com.bibliomania.BiblioMania.repository.NotificacionRepository;
import com.bibliomania.BiblioMania.repository.UserRepository;
import com.bibliomania.BiblioMania.repository.UsuariosGruposRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notiRepo;

    @Autowired
    private UserRepository usuarioRepo;

    @Autowired
    private MessageRepository messageRepo;
    
    @Autowired
    private UsuariosGruposRepository grupoRepo;
    
    public void crearNotificacion(Long userId, String titulo, String mensaje) {
        User usuario = usuarioRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Notificacion noti = new Notificacion();
        noti.setUsuario(usuario);
        noti.setTitulo(titulo);
        noti.setMensaje(mensaje);
        noti.setFechaCreacion(LocalDateTime.now());
        noti.setLeida(false);

        notiRepo.save(noti);
    }

    public List<NotificacionDTO> obtenerNotificaciones(Long userId) {
        return notiRepo.findByUsuarioIdOrderByFechaCreacionDesc(userId)
                .stream()
                .map(NotificacionDTO::new)
                .toList();
    }

    public void marcarComoLeida(Long notificacionId) {
        Notificacion noti = notiRepo.findById(notificacionId)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        noti.setLeida(true);
        notiRepo.save(noti);
    }
    
    public void notificarUsuariosDelHilo(Long threadId, Long autorComentarioId, String mensaje) {
        List<Long> usuarios = messageRepo.findDistinctUserIdsByThread(threadId);
        for (Long idUsuario : usuarios) {
            if (!idUsuario.equals(autorComentarioId)) {
                crearNotificacion(idUsuario, "Nuevo comentario", mensaje);
            }
        }
    }

    public void notificarNuevoHiloEnGrupo(Long grupoId, Long autorId, String tituloHilo) {
        List<Long> miembros = grupoRepo.findUserIdsByGrupoId(grupoId);
        for (Long idUsuario : miembros) {
            if (!idUsuario.equals(autorId)) {
                crearNotificacion(idUsuario, "Nuevo hilo en el grupo", tituloHilo);
            }
        }
    }

    
}
