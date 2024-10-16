package com.bibliomania.BiblioMania.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository usuarioRepository;

    public User registrarUsuario(User usuario) {
        return usuarioRepository.save(usuario);
    }

    public User login(String email, String password) {
        return usuarioRepository.findByEmail(email)
            .filter(user -> user.getPassword().equals(password))
            .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }
}