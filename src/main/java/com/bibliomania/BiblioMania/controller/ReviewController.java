package com.bibliomania.BiblioMania.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.service.ReviewService;
import com.bibliomania.BiblioMania.dto.ReviewDTO;
import com.bibliomania.BiblioMania.model.Review;
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        ReviewDTO review = reviewService.getReviewById(id);
        return review != null ? ResponseEntity.ok(review) : ResponseEntity.notFound().build();
    }

    @GetMapping("/libro/{libroIsbn}")
    public List<ReviewDTO> getReviewsByBookId(@PathVariable String libroIsbn) {
        return reviewService.getReviewsByBookId(libroIsbn);
    }
    
    @GetMapping("/user/{userId}")
    public List<ReviewDTO> getReviewsByUserId(@PathVariable Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }
    
    @PostMapping
    public ReviewDTO createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/usuario/{userId}/libro/{isbn}")
    public ResponseEntity<?> getReviewByUserAndBook(
            @PathVariable Long userId,
            @PathVariable String isbn) {
        try {
            Optional<ReviewDTO> review = reviewService.getReviewByUserAndBook(userId, isbn);
            return review.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Error interno al buscar la review");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/libro/{isbn}/media")
    public ResponseEntity<Double> getAverageRatingByBookIsbn(@PathVariable String isbn) {
        Double average = reviewService.getAverageRatingByBookIsbn(isbn);
        return ResponseEntity.ok(average);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody Review review) {
        ReviewDTO updated = reviewService.updateReview(id, review);
        return ResponseEntity.ok(updated);
    }


}
