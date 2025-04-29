package com.bibliomania.BiblioMania.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.model.Review;
import com.bibliomania.BiblioMania.repository.ReviewRepository;

@Service
public class ReviewService {
	   @Autowired
	    private ReviewRepository reviewRepository;

	    public List<Review> getAllReviews() {
	        return reviewRepository.findAll();
	    }

	    public Review getReviewById(Long id) {
	        return reviewRepository.findById(id).orElse(null);
	    }

	    public Review createReview(Review review) {
	        return reviewRepository.save(review);
	    }

	    public void deleteReview(Long id) {
	        reviewRepository.deleteById(id);
	    }
}
