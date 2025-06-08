package com.bibliomania.BiblioMania.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.dto.ActividadLecturaDTO;
import com.bibliomania.BiblioMania.model.ActividadLectura;
import com.bibliomania.BiblioMania.repository.ActividadLecturaRepository;
import com.bibliomania.BiblioMania.repository.BookRepository;
import com.bibliomania.BiblioMania.repository.UserRepository;

@Service
public class ActividadLecturaService {

    @Autowired
    private ActividadLecturaRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookRepository bookRepo;

    private ActividadLecturaDTO convertirAActividadLecturaDTO(ActividadLectura actividad) {
        ActividadLecturaDTO dto = new ActividadLecturaDTO();
        dto.setId(actividad.getId()); // Si tienes id en DTO, si no lo quitas
        dto.setUsuarioId(actividad.getUsuario().getId());
        dto.setIsbn(actividad.getLibro().getIsbn());
        dto.setDescripcion(actividad.getDescripcion());
        dto.setMinutosInvertidos(actividad.getMinutosInvertidos());
        dto.setFecha(actividad.getFecha());
        return dto;
    }
    
    public ActividadLecturaDTO crearActividad(ActividadLecturaDTO dto) {
        ActividadLectura act = new ActividadLectura();
        act.setUsuario(userRepo.findById(dto.getUsuarioId()).orElseThrow());
        act.setLibro(bookRepo.findByIsbn(dto.getIsbn()).orElseThrow());
        act.setDescripcion(dto.getDescripcion());
        act.setMinutosInvertidos(dto.getMinutosInvertidos());
        act.setFecha(dto.getFecha());

        ActividadLectura saved = repo.save(act);

        return new ActividadLecturaDTO(
            saved.getId(),
            saved.getUsuario().getId(),
            saved.getLibro().getIsbn(),
            saved.getDescripcion(),
            saved.getMinutosInvertidos(),
            saved.getFecha()
        );
    }

    public List<ActividadLecturaDTO> obtenerPorUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId).stream()
            .map(a -> new ActividadLecturaDTO(
                a.getId(),
                a.getUsuario().getId(),
                a.getLibro().getIsbn(),
                a.getDescripcion(),
                a.getMinutosInvertidos(),
                a.getFecha()
            ))
            .toList();
    }

    public ActividadLecturaDTO updateActividad(Long id, ActividadLecturaDTO dto) {
        ActividadLectura actividad = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        if (dto.getIsbn() != null) actividad.getLibro().setIsbn(dto.getIsbn());
        if (dto.getDescripcion() != null) actividad.setDescripcion(dto.getDescripcion());
        if (dto.getMinutosInvertidos() != null) actividad.setMinutosInvertidos(dto.getMinutosInvertidos());
        if (dto.getFecha() != null) actividad.setFecha(dto.getFecha());

        return convertirAActividadLecturaDTO(repo.save(actividad));
    }
    
    public void deleteActividad(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Actividad no encontrada");
        }
        repo.deleteById(id);
    }
    
    
    
}
