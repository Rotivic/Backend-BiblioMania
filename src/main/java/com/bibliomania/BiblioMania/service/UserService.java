package com.bibliomania.BiblioMania.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.dto.UsuarioDTO;
import com.bibliomania.BiblioMania.dto.UsuarioRegisterDTO;
import com.bibliomania.BiblioMania.exception.ResourceNotFoundException;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public UsuarioDTO registrarUsuario(UsuarioRegisterDTO  dto) {
        User usuario = maptToUser(dto);
        usuario.setVerificacionToken(UUID.randomUUID().toString());
        usuario.setVerified(false);
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setActivo(true);
        usuario.setRol("USER");
        User saved = usuarioRepository.save(usuario);
        emailService.sendEmail(saved.getEmail(), "Verificación", 
            "Verifica tu cuenta aquí: http://localhost:8080/api/usuarios/verify?token=" + saved.getVerificacionToken());
        return mapToDTO(saved);
    }

    public User login(String email, String password) {
        User user = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("Contraseña incorrecta");
        if (!user.isActivo())
            throw new RuntimeException("Cuenta inactiva");
        return user;
    }

    public boolean existeUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public UsuarioDTO obtenerUsuarioActual() {
        return mapToDTO(getAuthenticatedUser());
    }

    public UsuarioDTO actualizarUsuario(UsuarioDTO dto) {
        User usuario = getAuthenticatedUser();
        usuario.setName(dto.getName());
        usuario.setEmail(dto.getEmail());
        usuario.setProfileImageUrl(dto.getProfileImageUrl());
        usuario.setChatColor(dto.getChatColor());
        usuario.setBio(dto.getBio());
        usuario.setIdiomaPreferido(dto.getIdiomaPreferido());
        return mapToDTO(usuarioRepository.save(usuario));
    }

    public void cambiarPassword(String actual, String nueva) {
        User usuario = getAuthenticatedUser();
        if (!passwordEncoder.matches(actual, usuario.getPassword()))
            throw new RuntimeException("Contraseña actual incorrecta");
        usuario.setPassword(passwordEncoder.encode(nueva));
        usuarioRepository.save(usuario);
    }

    public void sendVerificacionEmail(String email) {
        String url = "http://localhost:8080/api/usuarios/verify-account?token=" + email;
        emailService.sendEmail(email, "Verificación de cuenta", "Haz clic en: " + url);
    }

    public void verifyAccount(String token) {
        User usuario = usuarioRepository.findByVerificacionToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Token inválido"));
        usuario.setVerified(true);
        usuario.setVerificacionToken(null);
        usuarioRepository.save(usuario);
    }

    public List<UsuarioDTO> obtenerTodosUsuarios() {
        return usuarioRepository.findByActivoTrue()
                .stream().map(this::mapToDTO).toList();
    }

    public List<UsuarioDTO> obtenerUsuariosDeshabilitados() {
        return usuarioRepository.findByActivoFalse()
                .stream().map(this::mapToDTO).toList();
    }

    public void reactivarUsuario(Long id) {
        User usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe usuario"));
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }

    public UsuarioDTO editarUsuario(Long id, UsuarioDTO dto) {
        User usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        usuario.setName(dto.getName());
        usuario.setEmail(dto.getEmail());
        usuario.setProfileImageUrl(dto.getProfileImageUrl());
        usuario.setChatColor(dto.getChatColor());
        usuario.setBio(dto.getBio());
        usuario.setIdiomaPreferido(dto.getIdiomaPreferido());
        return mapToDTO(usuarioRepository.save(usuario));
    }

    public void inhabilitarUsuario(Long id) {
        User usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    protected User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (principal instanceof UserDetails)
                ? ((UserDetails) principal).getUsername()
                : principal.toString();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No autenticado"));
    }

    private UsuarioDTO mapToDTO(User user) {
        return new UsuarioDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfileImageUrl(),
                user.getChatColor(),
                user.getBio(),
                user.getIdiomaPreferido(),
                user.getFechaRegistro(),
                user.getRol(),
                user.isActivo()
        );
    }

    private User maptToUser(UsuarioRegisterDTO dto) {
    	 User user = new User();
         user.setName(dto.getName());
         user.setEmail(dto.getEmail());
         user.setPassword(dto.getPassword());
         return user;
    }
    
    private User mapToEntity(UsuarioDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setProfileImageUrl(dto.getProfileImageUrl());
        user.setChatColor(dto.getChatColor());
        user.setBio(dto.getBio());
        user.setIdiomaPreferido(dto.getIdiomaPreferido());
        user.setFechaRegistro(dto.getFechaRegistro());
        user.setRol(dto.getRol());
        user.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return user;
    }
}
