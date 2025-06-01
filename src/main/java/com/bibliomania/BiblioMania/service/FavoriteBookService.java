package com.bibliomania.BiblioMania.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.FavoriteBook;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.BookRepository;
import com.bibliomania.BiblioMania.repository.FavoriteBookRepository;
import com.bibliomania.BiblioMania.repository.UserRepository;

@Service
public class FavoriteBookService {

    private final FavoriteBookRepository favoriteRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;
    private final NotificationDispatcher notificationDispatcher;

    public FavoriteBookService(FavoriteBookRepository favoriteRepo,
                               UserRepository userRepo,
                               BookRepository bookRepo,
                               NotificationDispatcher notificationDispatcher) {
        this.favoriteRepo = favoriteRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
        this.notificationDispatcher = notificationDispatcher;
    }

    public FavoriteBook addFavorite(Long userId, String bookId) {
        User user = userRepo.findById(userId).orElseThrow();
        Book book = bookRepo.findByIsbn(bookId).orElseThrow();

        if (favoriteRepo.findByUsuarioIdAndLibroIsbn(userId, bookId).isEmpty()) {
            FavoriteBook fav = new FavoriteBook();
            fav.setUsuario(user);
            fav.setLibro(book);
            FavoriteBook saved = favoriteRepo.save(fav);

            // Notificar al propio usuario
            notificationDispatcher.dispatchToUser(
                userId,
                "Libro añadido a favoritos",
                "Has añadido \"" + book.getTitle() + "\" a tus favoritos."
            );

            return saved;
        } else {
            throw new IllegalStateException("Libro ya está en favoritos.");
        }
    }

    public void removeFavorite(Long userId, String bookId) {
        favoriteRepo.deleteByUserIdAndBookIsbn(userId, bookId);
    }

    public List<FavoriteBook> getFavorites(Long userId) {
    	return favoriteRepo.findByUsuarioId(userId);
    }
}
