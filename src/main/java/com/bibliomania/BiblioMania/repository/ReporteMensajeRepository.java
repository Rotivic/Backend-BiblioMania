package com.bibliomania.BiblioMania.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bibliomania.BiblioMania.model.ReporteMensaje;

public interface ReporteMensajeRepository extends JpaRepository<ReporteMensaje, Long> {
    List<ReporteMensaje> findAllByOrderByFechaDesc();
    @Query("SELECT r FROM ReporteMensaje r WHERE r.reportadoPor.id = :usuarioId")
    List<ReporteMensaje> findByUsuarioReportante(@Param("usuarioId") Long usuarioId);


}