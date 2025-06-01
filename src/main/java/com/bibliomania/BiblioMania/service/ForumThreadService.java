package com.bibliomania.BiblioMania.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.model.ForumThread;
import com.bibliomania.BiblioMania.repository.ForumThreadRepository;
import com.bibliomania.BiblioMania.repository.UsuariosGruposRepository;

@Service
public class ForumThreadService {
    
    private final ForumThreadRepository forumThreadRepository;
    private final UsuariosGruposRepository usuariosGruposRepository;
    private final NotificationDispatcher notificationDispatcher;

    public ForumThreadService(ForumThreadRepository forumThreadRepository,
                              UsuariosGruposRepository usuariosGruposRepository,
                              NotificationDispatcher notificationDispatcher) {
        this.forumThreadRepository = forumThreadRepository;
        this.usuariosGruposRepository = usuariosGruposRepository;
        this.notificationDispatcher = notificationDispatcher;
    }

    public List<ForumThread> getForumThreadsByGroup(Long groupId) {
        return forumThreadRepository.findByGrupoId(groupId);
    }

    public ForumThread createThread(ForumThread forumThread) {
        ForumThread saved = forumThreadRepository.save(forumThread);

        // Notificar a los miembros del grupo
        List<Long> userIds = usuariosGruposRepository.findUserIdsByGrupoId(forumThread.getGrupo().getidGrupo());
        if (userIds != null && !userIds.isEmpty()) {
            notificationDispatcher.dispatchToUsers(
                userIds,
                "Nuevo hilo en el grupo",
                "Se ha creado un nuevo hilo: \"" + saved.getTitulo() + "\"."
            );
        }

        return saved;
    }
    
    public ForumThread toggleThreadStatus(Long id) {
        ForumThread thread = forumThreadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hilo no encontrado"));

        thread.setCerrado(!thread.isCerrado()); // cambia true ↔ false
        return forumThreadRepository.save(thread);
    }
}