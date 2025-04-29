package com.bibliomania.BiblioMania.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.dto.LibroDTO;
import com.bibliomania.BiblioMania.dto.ListaDTO;
import com.bibliomania.BiblioMania.dto.UsuarioDTO;
import com.bibliomania.BiblioMania.exception.ResourceNotFoundException;
import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.Lista;
import com.bibliomania.BiblioMania.model.ListaLibro;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.BookRepository;
import com.bibliomania.BiblioMania.repository.ListaRepository;

import jakarta.transaction.Transactional;

@Service
public class ListaService {
    
    @Autowired
    private ListaRepository listaRepository;
  
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserService userService;

    /**
     * Convierte una entidad Lista en un DTO ListaDTO.
     */
    private ListaDTO convertirAListaDTO(Lista lista) {
    	   return new ListaDTO(
    	            lista.getId(),
    	            lista.getNombre(),
    	            lista.getActivo(),
    	            lista.getUsuario() != null ? new UsuarioDTO(
    	                    lista.getUsuario().getId(), 
    	                    lista.getUsuario().getEmail(), 
    	                    lista.getUsuario().getNombre()
    	            ) : null,
    	            lista.getLibros() != null ? lista.getLibros().stream()
    	                    .map(libroLista -> libroLista.getLibro() != null ? new LibroDTO(
    	                            libroLista.getLibro().getId(),
    	                            libroLista.getLibro().getTitle(),
    	                            libroLista.getLibro().getAuthor(),
    	                            libroLista.getLibro().getIsbn(),
    	                            libroLista.getLibro().getDescription(),
    	                            libroLista.getLibro().isActivo()
    	                    ) : null)
    	                    .collect(Collectors.toList()) : null
    	    );
    }
    
    /**
     * Obtiene todas las listas en formato DTO.
     */
    public List<ListaDTO> getAllListas() {
        return listaRepository.findAll().stream()
                .map(this::convertirAListaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista por ID en formato DTO.
     */
    public ListaDTO getListaById(Long id) {
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista not found with id: " + id));
        return convertirAListaDTO(lista);
    }

    /**
     * Crea una nueva lista y la asocia con el usuario autenticado.
     */
    public ListaDTO createLista(Lista lista) {
        User user = userService.getAuthenticatedUser(); 
        lista.setUsuario(user);
        Lista savedLista = listaRepository.save(lista);
        return convertirAListaDTO(savedLista);
    }

    /**
     * Actualiza una lista existente.
     */
    public ListaDTO updateLista(Long id, Lista listaActualizada) {
        Lista listaExistente = listaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista not found with id: " + id));

        listaExistente.setActivo(listaActualizada.getActivo());
        listaExistente.setNombre(listaActualizada.getNombre());

        return convertirAListaDTO(listaRepository.save(listaExistente));
    }
    
    /**
     * Agrega un libro a una lista.
     */
    public ListaDTO addBookToLista(Long listaId, Long bookId) {
        Lista lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista not found with id: " + listaId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        if (lista.getLibros() == null) {
            lista.setLibros(new ArrayList<>());
        }

        ListaLibro listaLibro = new ListaLibro();
        listaLibro.setLista(lista);
        listaLibro.setLibro(book);

        lista.getLibros().add(listaLibro);

        return convertirAListaDTO(listaRepository.save(lista));
    }

    /**
     * Cambia el estado activo/inactivo de una lista.
     */
    public ListaDTO toggleListaStatus(Long id) {
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista not found with id: " + id));

        lista.setActivo(!lista.getActivo());
        return convertirAListaDTO(listaRepository.save(lista));
    }

    /**
     * Desactiva una lista (soft delete).
     */
    public void deleteLista(Long id) {
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista not found with id: " + id));

        lista.setActivo(false);
        listaRepository.save(lista);
    }
}
