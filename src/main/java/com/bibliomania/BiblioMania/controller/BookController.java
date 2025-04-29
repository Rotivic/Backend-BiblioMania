package com.bibliomania.BiblioMania.controller;

import com.bibliomania.BiblioMania.dto.LibroDTO;
import com.bibliomania.BiblioMania.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<LibroDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<LibroDTO> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @PutMapping("/isbn/{isbn}")
    public ResponseEntity<LibroDTO> updateBookByIsbn(@PathVariable String isbn, @RequestBody LibroDTO updatedLibroDTO) {
        return ResponseEntity.ok(bookService.updateBookByIsbn(isbn, updatedLibroDTO));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LibroDTO createBook(@RequestBody LibroDTO libroDTO) {
        return bookService.saveBook(libroDTO);
    }

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String isbn) {
        bookService.toggleLibroStatus(isbn);
    }
}
