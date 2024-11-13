package com.bibliomania.BiblioMania.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.exception.ResourceNotFoundException;
import com.bibliomania.BiblioMania.model.Lista;
import com.bibliomania.BiblioMania.repository.ListaRepository;


@Service
public class ListaService {
	  @Autowired
	    private ListaRepository listaRepository;

	  	public List<Lista> getAllListas() {
	        return listaRepository.findAll();
	    }

	    public Lista getListaById(Long id) {
	        return listaRepository.findById(id).orElse(null);
	    }

	    public Lista createLista(Lista lista) {
	        return listaRepository.save(lista);
	    }

	    public Lista updateLista(Long id, Lista lista) {
	    	Lista existingLista = listaRepository.findById(id)
	    	        .orElseThrow(() -> new ResourceNotFoundException("Lista not found with id: " + id));

	    	 existingLista.setNombre(lista.getNombre());
	    	 existingLista.setLibros(lista.getLibros());
	    	 existingLista.setUsuario(lista.getUsuario());
	    	 

	    	 return listaRepository.save(existingLista);
	    }
	    
	    public void deleteLista(Long id) {
	        listaRepository.deleteById(id);
	    }
}
