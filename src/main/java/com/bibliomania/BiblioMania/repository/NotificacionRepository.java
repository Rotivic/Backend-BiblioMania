package com.bibliomania.BiblioMania.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliomania.BiblioMania.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuarioIdOrderByFechaCreacionDesc(Long userId);
}
