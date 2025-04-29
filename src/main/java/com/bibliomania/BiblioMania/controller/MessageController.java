package com.bibliomania.BiblioMania.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bibliomania.BiblioMania.dto.MessageDTO;
import com.bibliomania.BiblioMania.model.Message;
import com.bibliomania.BiblioMania.service.MessageThreadService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/messages")
public class MessageController {

    private final MessageThreadService messageService;

    public MessageController(MessageThreadService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/forumThread/{threadId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByForumThread(@PathVariable Long threadId) {
        List<Message> messages = messageService.getMessagesByForumThread(threadId);
        List<MessageDTO> messageDTOs = messages.stream()
                .map(message -> new MessageDTO(message)) // Usar el constructor correcto
                .collect(Collectors.toList());
        return ResponseEntity.ok(messageDTOs);
    }


    @PostMapping("/forumThread/{threadId}/user/{userId}")
    public ResponseEntity<MessageDTO> createMessage(@PathVariable Long threadId,
                                                    @PathVariable Long userId,
                                                    @RequestBody Map<String, String> requestBody) {
        String contenido = requestBody.get("contenido");
        Message message = messageService.createMessage(threadId, userId, contenido);
        MessageDTO messageDTO = new MessageDTO(message);
        return ResponseEntity.ok(messageDTO);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok("Mensaje eliminado exitosamente.");
    }
}
