package com.bibliomania.BiblioMania.controller;

import com.bibliomania.BiblioMania.dto.CreateForumThreadDTO;
import com.bibliomania.BiblioMania.dto.ForumThreadDTO;
import com.bibliomania.BiblioMania.model.ForumThread;
import com.bibliomania.BiblioMania.model.GrupoLectura;
import com.bibliomania.BiblioMania.repository.GrupoLecturaRepository;
import com.bibliomania.BiblioMania.service.ForumThreadService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/forumThreads")
public class ForumThreadController {

    private final ForumThreadService forumThreadService;
    private final GrupoLecturaRepository grupoRepository;
    
    public ForumThreadController(ForumThreadService threadService, GrupoLecturaRepository grupoRepository) {
        this.forumThreadService = threadService;
        this.grupoRepository = grupoRepository;
    }

    @GetMapping("/group/{groupId}")
    public List<ForumThreadDTO> getThreadsByGroup(@PathVariable Long groupId) {
        return forumThreadService.getForumThreadsByGroup(groupId)
                .stream()
                .map(ForumThreadDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ForumThreadDTO createForumThread(@RequestBody CreateForumThreadDTO dto) {
    	GrupoLectura grupo = grupoRepository.findById(dto.getIdGrupo())
    	            .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        ForumThread thread = new ForumThread();
        thread.setTitulo(dto.getTitulo());
        thread.setGrupo(grupo);

        ForumThread creado = forumThreadService.createThread(thread);
        return new ForumThreadDTO(creado);
    }
    
    @PutMapping("/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede usarlo
    public ForumThreadDTO toggleThreadStatus(@PathVariable Long id) {
        ForumThread updated = forumThreadService.toggleThreadStatus(id);
        return new ForumThreadDTO(updated);
    }
    
}
