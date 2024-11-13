package com.bibliomania.BiblioMania.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.exception.ResourceNotFoundException;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.UserRepository;

@Service
public class UserService {
	
    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;
    
    public User registrarUsuario(User usuario) {
    	usuario.setVerificacionToken(UUID.randomUUID().toString());
    	usuario.setVerified(false);
    	usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    	
    	User savedUser = usuarioRepository.save(usuario);
    	
    	  // Construye la URL de verificación para el email
        String verificationUrl = "http://localhost:8080/api/usuarios/verify?token=" + savedUser.getVerificacionToken();

        // Envía el correo de verificación
        String subject = "Verificación de cuenta - BiblioMania";
        String message = "Gracias por registrarte en BiblioMania. Por favor, verifica tu cuenta haciendo clic en el siguiente enlace: \n" + verificationUrl;
        
        emailService.sendEmail(savedUser.getEmail(), subject, message);
    	
    	
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
    
    public void sendVerificacionEmail(String email) {
        String verificationUrl = "http://localhost:8080/api/users/verify-account?token=" + email;
        String subject = "Verificación de cuenta";
        String body = "Por favor, verifica tu cuenta haciendo clic en el siguiente enlace: " + verificationUrl;
        emailService.sendEmail(email, subject, body);
    }

    public void verifyAccount(String token) {
        User user = usuarioRepository.findByVerificacionToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Token no válido"));
        
        user.setVerified(true);
        user.setVerificacionToken(null); // Elimina el token después de la verificación
        usuarioRepository.save(user);
    }
    
}