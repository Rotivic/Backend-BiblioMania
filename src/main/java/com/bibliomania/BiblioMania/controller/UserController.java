package com.bibliomania.BiblioMania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.config.JwtUtil;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.UserRepository;
import com.bibliomania.BiblioMania.service.UserService;


@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService usuarioService;
    
    @Autowired
    private JwtUtil jwtTokenUtil; 
   
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody User usuario) {
        // Validación: Comprobamos si el email ya está registrado
    	 if (usuarioService.existeUsuarioPorEmail(usuario.getEmail())) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email ya está registrado.");
         }

        // Registramos el usuario y devolvemos la información del usuario creado
        User nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

  
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User usuario) {
    	   try {
               User autenticado = usuarioService.login(usuario.getEmail(), usuario.getPassword());
               String token = jwtTokenUtil.generateToken(autenticado.getEmail());
               return ResponseEntity.ok(token);
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
           }
    }
}