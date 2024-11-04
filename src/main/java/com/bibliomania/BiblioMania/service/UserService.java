package com.bibliomania.BiblioMania.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registrarUsuario(User usuario) {
    	usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public User login(String email, String password) {
    	// Buscar el usuario por email
        User user = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Petición muy larga"));

        // Comparar la contraseña usando el PasswordEncoder
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña no coincide");
        }
        return user;
    }
    
    public boolean existeUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }
}