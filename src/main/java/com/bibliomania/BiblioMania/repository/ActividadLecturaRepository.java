package com.bibliomania.BiblioMania.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bibliomania.BiblioMania.model.ActividadLectura;

public interface ActividadLecturaRepository extends JpaRepository<ActividadLectura, Long> {

    List<ActividadLectura> findByUsuarioId(Long usuarioId);

    @Query("SELECT a.fecha, SUM(a.minutosInvertidos) FROM ActividadLectura a WHERE a.usuario.id = :usuarioId GROUP BY a.fecha ORDER BY a.fecha")
    List<Object[]> sumMinutosPorFecha(@Param("usuarioId") Long usuarioId);

    @Query("SELECT SUM(a.minutosInvertidos) FROM ActividadLectura a WHERE a.usuario.id = :usuarioId")
    Integer totalMinutosPorUsuario(@Param("usuarioId") Long usuarioId);
}
