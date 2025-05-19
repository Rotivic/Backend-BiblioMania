package com.bibliomania.BiblioMania.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bibliomania.BiblioMania.dto.LibroLeidoDTO;
import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.LibroLeido;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.BookRepository;
import com.bibliomania.BiblioMania.repository.LibroLeidoRepository;
import com.bibliomania.BiblioMania.repository.UserRepository;

@Service
public class LibroLeidoService {

    private final LibroLeidoRepository libroLeidoRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LibroLeidoService(LibroLeidoRepository repo, UserRepository userRepo, BookRepository bookRepo) {
        this.libroLeidoRepository = repo;
        this.userRepository = userRepo;
        this.bookRepository = bookRepo;
    }

    @Transactional
    public LibroLeido marcarComoLeido(Long userId, String isbn) {
        if (libroLeidoRepository.existsByUsuarioIdAndLibroIsbn(userId, isbn)) {
            throw new IllegalStateException("El libro ya ha sido marcado como leído.");
        }

        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Book libro = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        LibroLeido leido = new LibroLeido();
        leido.setUsuario(usuario);
        leido.setLibro(libro);

        return libroLeidoRepository.save(leido);
    }

    public List<LibroLeidoDTO> obtenerLibrosLeidos(Long userId) {
        return libroLeidoRepository.findByUsuarioId(userId).stream()
                .map(l -> new LibroLeidoDTO(
                        l.getId(),
                        l.getLibro().getIsbn(),
                        l.getLibro().getTitle(),
                        l.getFechaLectura()
                ))
                .toList();
    }
}
