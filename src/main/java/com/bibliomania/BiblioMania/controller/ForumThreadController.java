package com.bibliomania.BiblioMania.controller;

import com.bibliomania.BiblioMania.dto.ForumThreadDTO;
import com.bibliomania.BiblioMania.model.ForumThread;
import com.bibliomania.BiblioMania.service.ForumThreadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/forumThreads")
public class ForumThreadController {

    private final ForumThreadService forumThreadService;

    public ForumThreadController(ForumThreadService threadService) {
        this.forumThreadService = threadService;
    }

    @GetMapping("/group/{groupId}")
    public List<ForumThreadDTO> getThreadsByGroup(@PathVariable Long groupId) {
        return forumThreadService.getForumThreadsByGroup(groupId)
                .stream()
                .map(ForumThreadDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ForumThreadDTO createForumThread(@RequestBody ForumThread forumThread) {
        return new ForumThreadDTO(forumThreadService.createThread(forumThread));
    }
}
