package com.bibliomania.BiblioMania.controller;

import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {
	 @Autowired
	    private BookService bookService;

	    @GetMapping
	    public List<Book> getAllBooks() {
	        return bookService.getAllBooks();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
	        Book book = bookService.getBookById(id);
	        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
	    }

	    @PostMapping
	    public Book createBook(@RequestBody Book book) {
	        return bookService.saveBook(book);
	    }

	    @PutMapping("/{id}")
	    public Book updateBook(@RequestBody Book book) {
			return bookService.updateBook(book);
	    	
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
	        bookService.deleteBook(id);
	        return ResponseEntity.noContent().build();
	    }
}
