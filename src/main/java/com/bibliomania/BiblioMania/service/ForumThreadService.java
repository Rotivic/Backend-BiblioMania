package com.bibliomania.BiblioMania.service;

import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.model.ForumThread;
import com.bibliomania.BiblioMania.repository.ForumThreadRepository;

import java.util.List;

@Service
public class ForumThreadService {
    
    private final ForumThreadRepository forumThreadRepository;
    
    public ForumThreadService(ForumThreadRepository forumThreadRepository) {
        this.forumThreadRepository = forumThreadRepository;
    }

    public List<ForumThread> getForumThreadsByGroup(Long groupId) {
        return forumThreadRepository.findByGrupoId(groupId);
    }

    public ForumThread createThread(ForumThread forumThread) {
        return forumThreadRepository.save(forumThread);
    }
}