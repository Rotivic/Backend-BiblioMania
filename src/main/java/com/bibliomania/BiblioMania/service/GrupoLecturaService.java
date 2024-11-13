package com.bibliomania.BiblioMania.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.repository.GrupoLecturaRepository;

import com.bibliomania.BiblioMania.model.GrupoLectura;

@Service
public class GrupoLecturaService {
    @Autowired
    private GrupoLecturaRepository grupoLecturaRepository;

    public List<GrupoLectura> getAllGrupos() {
        return grupoLecturaRepository.findAll();
    }

    public GrupoLectura getGrupoById(Long id) {
        return grupoLecturaRepository.findById(id).orElse(null);
    }

    public GrupoLectura createGrupo(GrupoLectura grupoLectura) {
        return grupoLecturaRepository.save(grupoLectura);
    }

    public void deleteGrupo(Long id) {
        grupoLecturaRepository.deleteById(id);
    }
}
