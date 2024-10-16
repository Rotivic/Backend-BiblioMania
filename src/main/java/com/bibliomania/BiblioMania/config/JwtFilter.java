package com.bibliomania.BiblioMania.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain; // Cambiado de javax a jakarta
import jakarta.servlet.ServletException; // Cambiado de javax a jakarta
import jakarta.servlet.http.HttpServletRequest; // Cambiado de javax a jakarta
import jakarta.servlet.http.HttpServletResponse; // Cambiado de javax a jakarta
import java.io.IOException;

@Component // Asegúrate de tener esta anotación
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Aquí va tu lógica para filtrar el JWT
        String token = extractToken(request);
        if (token != null && jwtUtil.validateToken(token)) {
            // Lógica para establecer la autenticación
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
