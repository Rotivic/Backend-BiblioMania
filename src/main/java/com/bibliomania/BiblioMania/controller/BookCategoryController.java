package com.bibliomania.BiblioMania.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.BookCategory;
import com.bibliomania.BiblioMania.model.Category;
import com.bibliomania.BiblioMania.repository.BookRepository;
import com.bibliomania.BiblioMania.repository.CategoryRepository;
import com.bibliomania.BiblioMania.service.BookCategoryService;

@RestController
@RequestMapping("/api/book-categories")
public class BookCategoryController {

    @Autowired
    private BookCategoryService bookCategoryService;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @PostMapping
    public ResponseEntity<?> assignCategoryToBook(@RequestParam Long bookId, @RequestParam Long categoryId) {
        Optional<Book> bookOpt = bookRepo.findById(bookId);
        Optional<Category> catOpt = categoryRepo.findById(categoryId);

        if (bookOpt.isEmpty() || catOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Libro o categoría no encontrada.");
        }

        // Nueva verificación: ya está asignada la categoría al libro
        List<BookCategory> existing = bookCategoryService.findByBookId(bookId);
        boolean alreadyAssigned = existing.stream()
            .anyMatch(bc -> bc.getCategoria().getId().equals(categoryId));

        if (alreadyAssigned) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya asignado.");
        }

        BookCategory bookCategory = new BookCategory();
        bookCategory.setLibro(bookOpt.get());
        bookCategory.setCategoria(catOpt.get());

        return ResponseEntity.ok(bookCategoryService.save(bookCategory));
    }
    
    @GetMapping("/libros/{categoryId}")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable Long categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            return ResponseEntity.notFound().build();
        }

        List<Book> libros = bookCategoryService.findByCategoryId(categoryId).stream()
            .map(BookCategory::getLibro)
            .filter(Book::getActivo)
            .distinct()
            .toList();

        return ResponseEntity.ok(libros);
    }
    
}
