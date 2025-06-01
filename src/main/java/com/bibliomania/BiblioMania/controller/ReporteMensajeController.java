package com.bibliomania.BiblioMania.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.dto.ReporteMensajeDTO;
import com.bibliomania.BiblioMania.model.enums.EstadoReporte;
import com.bibliomania.BiblioMania.service.ReporteMensajeService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteMensajeController {

    private final ReporteMensajeService reporteMensajeService;

    public ReporteMensajeController(ReporteMensajeService reporteMensajeService) {
        this.reporteMensajeService = reporteMensajeService;
    }

    @PostMapping
    public ResponseEntity<ReporteMensajeDTO> crearReporte(@RequestBody ReporteMensajeDTO dto) {
        ReporteMensajeDTO creado = reporteMensajeService.crearReporte(dto);
        return ResponseEntity.ok(creado);
    }

	@GetMapping
	public ResponseEntity<List<ReporteMensajeDTO>> obtenerTodos() {
	    return ResponseEntity.ok(reporteMensajeService.obtenerTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReporteMensajeDTO> obtenerPorId(@PathVariable Long id) {
	    return ResponseEntity.ok(reporteMensajeService.obtenerPorId(id));
	}
	
	@PatchMapping("/{id}/estado")
	public ResponseEntity<ReporteMensajeDTO> cambiarEstado(
	    @PathVariable Long id,
	    @RequestParam EstadoReporte estado
	) {
	    return ResponseEntity.ok(reporteMensajeService.actualizarEstado(id, estado));
	}
    
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<ReporteMensajeDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
	    return ResponseEntity.ok(reporteMensajeService.obtenerPorUsuario(usuarioId));
	}
	
}
