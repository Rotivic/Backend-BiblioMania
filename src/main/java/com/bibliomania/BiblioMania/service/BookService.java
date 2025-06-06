package com.bibliomania.BiblioMania.service;

import com.bibliomania.BiblioMania.dto.LibroDTO;
import com.bibliomania.BiblioMania.exception.ResourceNotFoundException;
import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    private LibroDTO convertirABookDTO(Book book) {
        return new LibroDTO(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getDescription(),
            book.isActivo(),
            book.getPortadaUrl(),
            book.getPaginas(),
            book.getAnioPublicacion(),
            book.getEditorial()
        );
    }

    private Book convertirAEntidad(LibroDTO libroDTO) {
        Book book = new Book();
        book.setId(libroDTO.getId());
        book.setTitle(libroDTO.getTitle());
        book.setAuthor(libroDTO.getAuthor());
        book.setIsbn(libroDTO.getIsbn());
        book.setDescription(libroDTO.getDescription());
        book.setActivo(libroDTO.isActivo());
        book.setPortadaUrl(libroDTO.getPortadaUrl());
        book.setPaginas(libroDTO.getPaginas());
        book.setAnioPublicacion(libroDTO.getAnioPublicacion());
        book.setEditorial(libroDTO.getEditorial());
        return book;
    }

    public List<LibroDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertirABookDTO)
                .collect(Collectors.toList());
    }

    public LibroDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .filter(Book::isActivo)
                .map(this::convertirABookDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + id));
    }

    public LibroDTO getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .filter(Book::isActivo)
                .map(this::convertirABookDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ISBN: " + isbn));
    }

    public LibroDTO saveBook(LibroDTO libroDTO) {
        Book book = convertirAEntidad(libroDTO);
        book.setActivo(true);
        return convertirABookDTO(bookRepository.save(book));
    }

    public LibroDTO updateBookByIsbn(String isbn, LibroDTO updatedLibroDTO) {
        Book existingBook = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ISBN: " + isbn));

        if (updatedLibroDTO.getTitle() != null) {
            existingBook.setTitle(updatedLibroDTO.getTitle());
        }

        if (updatedLibroDTO.getAuthor() != null) {
            existingBook.setAuthor(updatedLibroDTO.getAuthor());
        }

        if (updatedLibroDTO.getDescription() != null) {
            existingBook.setDescription(updatedLibroDTO.getDescription());
        }

        if (updatedLibroDTO.getEditorial() != null) {
            existingBook.setEditorial(updatedLibroDTO.getEditorial());
        }

        if (updatedLibroDTO.getAnioPublicacion() != null) {
            existingBook.setAnioPublicacion(updatedLibroDTO.getAnioPublicacion());
        }

        if (updatedLibroDTO.getPaginas() != null) {
            existingBook.setPaginas(updatedLibroDTO.getPaginas());
        }

        if (updatedLibroDTO.getPortadaUrl() != null) {
            existingBook.setPortadaUrl(updatedLibroDTO.getPortadaUrl());
        }


        
        
        return convertirABookDTO(bookRepository.save(existingBook));
    }

    public void toggleLibroStatus(String isbn) {
        Book libro = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
        libro.setActivo(!libro.isActivo());
        bookRepository.save(libro);
    }
}
