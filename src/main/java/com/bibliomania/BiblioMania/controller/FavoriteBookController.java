package com.bibliomania.BiblioMania.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.dto.FavoriteBookDTO;
import com.bibliomania.BiblioMania.exception.ResourceNotFoundException;
import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.FavoriteBook;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.BookRepository;
import com.bibliomania.BiblioMania.repository.FavoriteBookRepository;
import com.bibliomania.BiblioMania.repository.UserRepository;
import com.bibliomania.BiblioMania.service.FavoriteBookService;

@RestController
@RequestMapping("api/favoritos")
public class FavoriteBookController {

	 @Autowired
	    private FavoriteBookRepository favoriteBookRepository;

	    @Autowired
	    private BookRepository bookRepository;

	    @Autowired
	    private UserRepository userRepository;
	
    private final FavoriteBookService favoriteBookService;

    public FavoriteBookController(FavoriteBookService favoriteBookService) {
        this.favoriteBookService = favoriteBookService;
    }

    @PostMapping
    public ResponseEntity<FavoriteBookDTO> addFavorite(@RequestBody Map<String, String> payload) {
        Long userId = Long.valueOf(payload.get("userId"));
        String isbn = payload.get("isbn");

        Book book = bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ISBN: " + isbn));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        FavoriteBook favorite = new FavoriteBook();
        favorite.setUsuario(user);
        favorite.setLibro(book);

        FavoriteBook saved = favoriteBookRepository.save(favorite);
        return ResponseEntity.ok(new FavoriteBookDTO(saved));
    }

    @DeleteMapping("/remove/user/{userId}/book/{isbn}")
    public ResponseEntity<?> removeFavorite(@PathVariable Long userId, @PathVariable String isbn) {
        favoriteBookService.removeFavorite(userId, isbn);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteBookDTO>> getFavorites(@PathVariable Long userId) {
        List<FavoriteBookDTO> dtos = favoriteBookService.getFavorites(userId)
            .stream()
            .map(FavoriteBookDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
