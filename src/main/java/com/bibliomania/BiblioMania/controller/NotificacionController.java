package com.bibliomania.BiblioMania.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.dto.NotificacionDTO;
import com.bibliomania.BiblioMania.service.NotificacionService;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notiService;

    @GetMapping("/{userId}")
    public List<NotificacionDTO> getNotificaciones(@PathVariable Long userId) {
        return notiService.obtenerNotificaciones(userId);
    }

    @PostMapping("/{userId}")
    public void crearNotificacion(@PathVariable Long userId, @RequestBody Map<String, String> body) {
        String titulo = body.get("titulo");
        String mensaje = body.get("mensaje");
        notiService.crearNotificacion(userId, titulo, mensaje);
    }

    @PutMapping("/leer/{id}")
    public void marcarComoLeida(@PathVariable Long id) {
        notiService.marcarComoLeida(id);
    }
}
