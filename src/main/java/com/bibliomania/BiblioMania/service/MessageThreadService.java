package com.bibliomania.BiblioMania.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
	private final NotificationDispatcher notificationDispatcher;

	public MessageThreadService(MessageRepository messageRepository, ForumThreadRepository forumThreadRepository,
			UserRepository userRepository, NotificationDispatcher notificationDispatcher) {
		this.messageRepository = messageRepository;
		this.forumThreadRepository = forumThreadRepository;
		this.userRepository = userRepository;
		this.notificationDispatcher = notificationDispatcher;
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

		if (forumThread.isCerrado()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Este hilo está cerrado y no se pueden enviar mensajes.");
		}

		User usuario = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + userId));

		Message message = new Message();
		message.setContenido(contenido);
		message.setForumThread(forumThread);
		message.setUsuario(usuario);
		message.setFechaEnvio(LocalDateTime.now());

		Message saved = messageRepository.save(message);

		// Notificar a los participantes anteriores del hilo, excepto al autor del
		// mensaje
		List<Long> participantIds = messageRepository.findDistinctUserIdsByThread(threadId);
		participantIds.removeIf(id -> id.equals(userId)); // excluir al que escribe

		if (!participantIds.isEmpty()) {
			notificationDispatcher.dispatchToUsers(participantIds, "Nuevo mensaje en hilo", usuario.getName()
					+ " ha publicado un nuevo mensaje en el hilo \"" + forumThread.getTitulo() + "\".");
		}

		return saved;
	}

	public void deleteMessage(Long messageId) {
		if (!messageRepository.existsById(messageId)) {
			throw new ResourceNotFoundException("Mensaje no encontrado con id: " + messageId);
		}
		messageRepository.deleteById(messageId);
	}
	
	public Message getMessageById(Long messageId) {
	    return messageRepository.findById(messageId)
	        .orElseThrow(() -> new ResourceNotFoundException("Mensaje no encontrado con id: " + messageId));
	}
}
