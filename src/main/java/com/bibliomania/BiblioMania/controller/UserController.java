package com.bibliomania.BiblioMania.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.config.JwtUtil;
import com.bibliomania.BiblioMania.dto.PasswordChangeRequest;
import com.bibliomania.BiblioMania.dto.UsuarioDTO;
import com.bibliomania.BiblioMania.dto.UsuarioRegisterDTO;
import com.bibliomania.BiblioMania.exception.ResourceNotFoundException;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService usuarioService;
    
    @Autowired
    private JwtUtil jwtTokenUtil; 
   
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioRegisterDTO usuario, BindingResult result) {
        if (result.hasErrors()) {
            // Devuelve el primer error encontrado (puedes personalizar esto)
            String errorMsg = result.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }

        if (usuarioService.existeUsuarioPorEmail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email ya está registrado.");
        }

        UsuarioDTO nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

  
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioRegisterDTO usuario) {
    	   try {
               User autenticado = usuarioService.login(usuario.getEmail(), usuario.getPassword());
               String token = jwtTokenUtil.generateToken(autenticado.getEmail());
               String role = autenticado.getRol();
               return ResponseEntity.ok(Map.of("token", token, "role", role));
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e));
           }
    }
    
    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> obtenerPerfil() {
        UsuarioDTO usuario = usuarioService.obtenerUsuarioActual();
        return ResponseEntity.ok(usuario);
    }
    
    @PostMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody PasswordChangeRequest request) {
        usuarioService.cambiarPassword(request.getPasswordActual(), request.getPasswordNueva());
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada"));
    }
    
    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarUsuario(@RequestBody UsuarioDTO usuarioActualizado) {
        UsuarioDTO actualizado = usuarioService.actualizarUsuario(usuarioActualizado);
        return ResponseEntity.ok(actualizado);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
       
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token no encontrado en la solicitud");
    }

    @PostMapping("/send-verification-email")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody String email) {
        try {
        	usuarioService.sendVerificacionEmail(email);
            return ResponseEntity.ok("Email de verificación enviado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al enviar el email de verificación.");
        }
    }
    
    @GetMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        try {
        	usuarioService.verifyAccount(token);
            return ResponseEntity.ok("Cuenta verificada exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Token no válido o expirado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al verificar la cuenta.");
        }
    }
    
    // Obtener usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuariosActivos() {
        List<UsuarioDTO> usuariosActivos = usuarioService.obtenerTodosUsuarios()
                .stream()
                .filter(UsuarioDTO::isActivo) // Solo los verificados
                .toList();
        return ResponseEntity.ok(usuariosActivos);
    }

    // Obtener usuarios deshabilitados
    @GetMapping("/deshabilitados")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuariosDeshabilitados() {
        return (ResponseEntity<List<UsuarioDTO>>) ResponseEntity.ok(usuarioService.obtenerUsuariosDeshabilitados());
    }

    // Reactivar usuario
    @PutMapping("/reactivar/{id}")
    public ResponseEntity<String> reactivarUsuario(@PathVariable Long id) {
    	usuarioService.reactivarUsuario(id);
        return ResponseEntity.ok("Usuario reactivado exitosamente");
    }

    // 🔹 Editar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioActualizado) {
        try {
            UsuarioDTO actualizado = usuarioService.editarUsuario(id, usuarioActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    // 🔹 Inhabilitar usuario (Soft Delete)
    @PutMapping("/{id}/inhabilitar")
    public ResponseEntity<?> inhabilitarUsuario(@PathVariable Long id) {
        try {
            usuarioService.inhabilitarUsuario(id);
            return ResponseEntity.ok("Usuario inhabilitado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
    
}