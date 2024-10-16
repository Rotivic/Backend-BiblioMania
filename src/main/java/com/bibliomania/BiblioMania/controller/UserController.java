package com.bibliomania.BiblioMania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.service.UserService;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

	 @Autowired
	    private UserService usuarioService;

	    @PostMapping("/register")
	    public ResponseEntity<User> registrarUsuario(@RequestBody User usuario) {
	    	User nuevoUsuario = usuarioService.registrarUsuario(usuario);
	        return ResponseEntity.ok(nuevoUsuario);
	    }

	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody User usuario) {
	        try {
	        	User autenticado = usuarioService.login(usuario.getEmail(), usuario.getPassword());
	            return ResponseEntity.ok("Login exitoso");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
	        }
	    }
	
}
