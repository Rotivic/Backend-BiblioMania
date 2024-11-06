package com.bibliomania.BiblioMania.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.config.JwtUtil;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.UserRepository;
import com.bibliomania.BiblioMania.service.UserService;

import dto.PasswordChangeRequest;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService usuarioService;
    
    @Autowired
    private JwtUtil jwtTokenUtil; 
   
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody User usuario) {
 
    	 if (usuarioService.existeUsuarioPorEmail(usuario.getEmail())) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email ya está registrado.");
         }

        User nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

  
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User usuario) {
    	   try {
               User autenticado = usuarioService.login(usuario.getEmail(), usuario.getPassword());
               String token = jwtTokenUtil.generateToken(autenticado.getEmail());
               return ResponseEntity.ok(Map.of("token", token));
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales inválidas"));
           }
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> obtenerPerfil() {
        User usuario = usuarioService.obtenerUsuarioActual();
        return ResponseEntity.ok(usuario);
    }
    
    @PostMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody PasswordChangeRequest request) {
        usuarioService.cambiarPassword(request.getPasswordActual(), request.getPasswordNueva());
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada"));
    }
    
    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarUsuario(@RequestBody User usuarioActualizado) {
        User actualizado = usuarioService.actualizarUsuario(usuarioActualizado);
        return ResponseEntity.ok(actualizado);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
       
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token no encontrado en la solicitud");
    }
    
}