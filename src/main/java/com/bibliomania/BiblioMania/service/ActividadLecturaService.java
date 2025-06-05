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

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
