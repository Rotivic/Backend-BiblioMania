package com.bibliomania.BiblioMania.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bibliomania.BiblioMania.exception.ResourceNotFoundException;
import com.bibliomania.BiblioMania.model.ForumThread;
import com.bibliomania.BiblioMania.model.Message;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.ForumThreadRepository;
import com.bibliomania.BiblioMania.repository.MessageRepository;
import com.bibliomania.BiblioMania.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageThreadService {
    
    private final MessageRepository messageRepository;
    private final ForumThreadRepository forumThreadRepository;
    private final UserRepository userRepository;

    public MessageThreadService(MessageRepository messageRepository, 
                                ForumThreadRepository forumThreadRepository, 
                                UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.forumThreadRepository = forumThreadRepository;
        this.userRepository = userRepository;
    }

    public List<Message> getMessagesByForumThread(Long threadId) {
        return messageRepository.findByForumThread_IdOrderByFechaEnvioAsc(threadId);
    }

    @Transactional
    public Message createMessage(Long threadId, Long userId, String contenido) {
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío.");
        }

        ForumThread forumThread = forumThreadRepository.findById(threadId)
                .orElseThrow(() -> new ResourceNotFoundException("Hilo no encontrado con id: " + threadId));

        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + userId));

        Message message = new Message();
        message.setContenido(contenido);
        message.setForumThread(forumThread);
        message.setUsuario(usuario);
        message.setFechaEnvio(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public void deleteMessage(Long messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new ResourceNotFoundException("Mensaje no encontrado con id: " + messageId);
        }
        messageRepository.deleteById(messageId);
    }
}
