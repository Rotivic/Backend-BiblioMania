package com.bibliomania.BiblioMania.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.dto.LibroDTO;
import com.bibliomania.BiblioMania.dto.ReviewDTO;
import com.bibliomania.BiblioMania.dto.UsuarioDTO;
import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.Review;
import com.bibliomania.BiblioMania.model.User;
import com.bibliomania.BiblioMania.repository.BookRepository;
import com.bibliomania.BiblioMania.repository.ReviewRepository;
import com.bibliomania.BiblioMania.repository.UserRepository;

@Service
public class ReviewService {
	   @Autowired
	    private ReviewRepository reviewRepository;

	   @Autowired
	   private BookRepository bookRepository;
	   
	   @Autowired
	   private UserRepository userRepository;
	   
	   private ReviewDTO convertirAReviewDTO(Review review) {
		    ReviewDTO dto = new ReviewDTO();
		    dto.setId(review.getId());
		    dto.setComentario(review.getComentario());
		    dto.setCalificacion(review.getCalificacion());
		    if (review.getUsuario() != null) {
		        UsuarioDTO usuarioDTO = new UsuarioDTO();
		        usuarioDTO.setId(review.getUsuario().getId());
		        usuarioDTO.setEmail(review.getUsuario().getEmail());
		        usuarioDTO.setNombre(review.getUsuario().getName());
		        dto.setUsuario(usuarioDTO);
		    }
		    if (review.getLibro() != null) {
		        LibroDTO libroDTO = new LibroDTO();
		        libroDTO.setId(review.getLibro().getId());
		        libroDTO.setTitle(review.getLibro().getTitle());
		        libroDTO.setAuthor(review.getLibro().getAuthor());
		        libroDTO.setDescription(review.getLibro().getDescription());
		        libroDTO.setIsbn(review.getLibro().getIsbn());
		        libroDTO.setActivo(review.getLibro().getActivo());
		        dto.setLibro(libroDTO);
		    }
		    return dto;
		}
	   
	    public List<ReviewDTO> getAllReviews() {
	        return reviewRepository.findAll().stream()
	                .map(this::convertirAReviewDTO)
	                .collect(Collectors.toList());
	    }

	    public List<ReviewDTO> getReviewsByBookId(String libroIsbn) {
	        return reviewRepository.findByLibroIsbn(libroIsbn)
	            .stream()
	            .map(this::convertirAReviewDTO)
	            .collect(Collectors.toList());
	    }
	    
	    public List<ReviewDTO> getReviewsByUserId(Long userId) {
	        return reviewRepository.findByUsuarioId(userId)
	            .stream()
	            .map(this::convertirAReviewDTO)
	            .collect(Collectors.toList());
	    }
	    
	    public ReviewDTO getReviewById(Long id) {
	        Review review = reviewRepository.findById(id).orElse(null);
	        return review != null ? convertirAReviewDTO(review) : null;
	    }

	    // Para crear y devolver el DTO recién creado
	    public ReviewDTO createReview(Review review) {
	        // Buscar el libro por ISBN
	        String isbn = review.getLibro().getIsbn();
	        Book libro = bookRepository.findByIsbn(isbn)
	            .orElseThrow(() -> new RuntimeException("Libro no encontrado con ISBN: " + isbn));
	        review.setLibro(libro);

	        Optional<Review> existingReview = reviewRepository.findByUsuarioIdAndLibroIsbn(
	                review.getUsuario().getId(),
	                review.getLibro().getIsbn()
	            );
	            if (existingReview.isPresent()) {
	                throw new RuntimeException("El usuario ya ha creado una review para este libro.");
	                // O lanza una excepción personalizada y devuélvela como 409 Conflict
	            }
	        
	        // (Opcional) Validar usuario por ID
	        Long userId = review.getUsuario().getId();
	        User usuario = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
	        review.setUsuario(usuario);

	        Review saved = reviewRepository.save(review);
	        return convertirAReviewDTO(saved);
	    }

	    public void deleteReview(Long id) {
	        reviewRepository.deleteById(id);
	    }
	    
	    public Optional<ReviewDTO> getReviewByUserAndBook(Long userId, String isbn) {
	        return reviewRepository.findByUsuarioIdAndLibroIsbn(userId, isbn)
	            .map(this::convertirAReviewDTO);
	    }
	    public Double getAverageRatingByBookIsbn(String isbn) {
	        Double avg = reviewRepository.findAverageCalificacionByLibroIsbn(isbn);
	        return avg != null ? avg : 0.0;
	    }

	    public ReviewDTO updateReview(Long id, Review reviewData) {
	        Review review = reviewRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Review no encontrada con id: " + id));

	        // Actualiza solo los campos editables
	        review.setComentario(reviewData.getComentario());
	        review.setCalificacion(reviewData.getCalificacion());

	        // (Opcional) Si quieres permitir cambiar el libro o usuario, deberías validarlo aquí.
	        // Normalmente, no se suele cambiar el libro ni el usuario de una review existente.

	        Review saved = reviewRepository.save(review);
	        return convertirAReviewDTO(saved);
	    }


}
