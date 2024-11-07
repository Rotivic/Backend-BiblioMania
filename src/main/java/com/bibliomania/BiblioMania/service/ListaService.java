package com.bibliomania.BiblioMania.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.model.Lista;
import com.bibliomania.BiblioMania.repository.ListaRepository;

@Service
public class ListaService {
	  @Autowired
	    private ListaRepository listaRepository;

	    public Lista crearLista(Lista lista) {
	        return listaRepository.save(lista);
	    }

	    public List<Lista> obtenerListas() {
	        return listaRepository.findAll();
	    }
}
