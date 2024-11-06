package com.bibliomania.BiblioMania.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        User user = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Petición muy larga"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña no coincide");
        }
        return user;
    }
    
    public boolean existeUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public User obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public User actualizarUsuario(User usuarioActualizado) {
        User usuarioExistente = obtenerUsuarioActual();

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());

        return usuarioRepository.save(usuarioExistente);
    }

    public void cambiarPassword(String passwordActual, String passwordNueva) {
        User usuarioActual = obtenerUsuarioActual();

        if (!passwordEncoder.matches(passwordActual, usuarioActual.getPassword())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        usuarioActual.setPassword(passwordEncoder.encode(passwordNueva));
        
    }
    
}