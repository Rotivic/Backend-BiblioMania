package com.bibliomania.BiblioMania.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.dto.BookCategoryDTO;
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
    public ResponseEntity<?> assignCategoryToBook(@RequestParam String isbn, @RequestParam Long categoryId) {
        Optional<Book> bookOpt = bookRepo.findByIsbn(isbn);
        Optional<Category> catOpt = categoryRepo.findById(categoryId);

        if (bookOpt.isEmpty() || catOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Libro o categoría no encontrada.");
        }

        Book book = bookOpt.get();

        List<BookCategory> existing = bookCategoryService.findByBookId(book.getId());
        boolean alreadyAssigned = existing.stream()
            .anyMatch(bc -> bc.getCategoria().getId().equals(categoryId));

        if (alreadyAssigned) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya asignado.");
        }

        BookCategory bookCategory = new BookCategory();
        bookCategory.setLibro(book);
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
    
    @DeleteMapping
    public ResponseEntity<?> unassignCategoryFromBook(@RequestBody BookCategoryDTO dto) {
        Optional<Book> bookOpt = bookRepo.findByIsbn(dto.getLibroIsbn());
        Optional<Category> catOpt = categoryRepo.findById(dto.getCategoriaId());

        if (bookOpt.isEmpty() || catOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Libro o categoría no encontrada.");
        }

        Book book = bookOpt.get();
        Category category = catOpt.get();

        Optional<BookCategory> bcOpt = bookCategoryService.findByBookId(book.getId()).stream()
            .filter(bc -> bc.getCategoria().getId().equals(category.getId()))
            .findFirst();

        if (bcOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Relación no existente.");
        }

        bookCategoryService.delete(bcOpt.get());
        return ResponseEntity.ok("Categoría desasignada del libro correctamente.");
    }
}

