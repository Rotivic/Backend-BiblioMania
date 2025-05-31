package com.bibliomania.BiblioMania.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.dto.LibroDTO;
import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.BookCategory;
import com.bibliomania.BiblioMania.repository.BookCategoryRepository;
import com.bibliomania.BiblioMania.repository.FavoriteBookRepository;
import com.bibliomania.BiblioMania.repository.LibroLeidoRepository;
import com.bibliomania.BiblioMania.repository.ReviewRepository;

@Service
public class RecommendationService {

    @Autowired
    private BookCategoryRepository bookCategoryRepo;

    @Autowired
    private FavoriteBookRepository favoriteRepo;

    @Autowired
    private LibroLeidoRepository leidoRepo;

    @Autowired
    private ReviewRepository reviewRepo;
    
    private LibroDTO convertToDTO(Book book) {
        return new LibroDTO(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getDescription(),
            book.isActivo()
        );
    }
    
    public List<LibroDTO> getRecommendedBooksByCategory(Long userId) {
        Map<Long, Integer> categoryPopularity = new HashMap<>();
        Map<Long, List<BookCategory>> bookCategoryCache = new HashMap<>();

        Set<Long> excluidos = Stream.concat(
                favoriteRepo.findByUsuarioId(userId).stream()
                    .map(fav -> fav.getLibro().getId()),
                leidoRepo.findByUsuarioId(userId).stream()
                    .map(leido -> leido.getLibro().getId())
            ).collect(Collectors.toSet());
        
        // Obtener IDs de libros favoritos y leídos
        Stream<Long> favoritosIds = favoriteRepo.findByUsuarioId(userId).stream()
            .map(fav -> fav.getLibro().getId());

        Stream<Long> leidosIds = leidoRepo.findByUsuarioId(userId).stream()
            .map(leido -> leido.getLibro().getId());

        Stream<Long> libroIds = Stream.concat(favoritosIds, leidosIds).distinct();

        libroIds.forEach(bookId -> {
            List<BookCategory> categories = bookCategoryCache.computeIfAbsent(
                bookId,
                id -> bookCategoryRepo.findByLibroId(id)
            );

            for (BookCategory bc : categories) {
                categoryPopularity.merge(bc.getCategoria().getId(), 1, Integer::sum);
            }
        });

        // Ordenar y obtener top 5 categorías
        List<Long> topCategoryIds = categoryPopularity.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .map(Map.Entry::getKey)
            .limit(5)
            .toList();

        // Obtener libros más relevantes por categoría
        List<LibroDTO> result = new ArrayList<>();
        for (Long categoryId : topCategoryIds) {
            List<LibroDTO> booksInCat = bookCategoryRepo.findByCategoriaId(categoryId).stream()
                .map(BookCategory::getLibro)
                .filter(Book::getActivo)
                .filter(libro -> !excluidos.contains(libro.getId()))
                .distinct()
                .sorted((b1, b2) -> {
                    Double avg1 = reviewRepo.findAverageCalificacionByLibroIsbn(b1.getIsbn());
                    Double avg2 = reviewRepo.findAverageCalificacionByLibroIsbn(b2.getIsbn());

                    if (avg1 == null) return 1;
                    if (avg2 == null) return -1;

                    return Double.compare(avg2, avg1);
                })
                .limit(3)
                .map(this::convertToDTO)
                .toList();

            result.addAll(booksInCat);
        }

        return result;
    }
    
    public List<LibroDTO> getTopRatedBooks(int limit) {
        return bookCategoryRepo.findAll().stream()
            .map(BookCategory::getLibro)
            .filter(Book::getActivo)
            .distinct()
            .sorted((b1, b2) -> {
                Double avg1 = reviewRepo.findAverageCalificacionByLibroIsbn(b1.getIsbn());
                Double avg2 = reviewRepo.findAverageCalificacionByLibroIsbn(b2.getIsbn());

                // nulls al final
                if (avg1 == null) return 1;
                if (avg2 == null) return -1;

                return Double.compare(avg2, avg1); // orden descendente
            })
            .limit(limit)
            .map(this::convertToDTO)
            .toList();
    }

}

