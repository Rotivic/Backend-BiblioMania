package com.bibliomania.BiblioMania.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.model.Review;
import com.bibliomania.BiblioMania.repository.ReviewRepository;

@Service
public class ReviewService {
	   @Autowired
	    private ReviewRepository reviewRepository;

	    public Review publicarReseña(Review reseña) {
	        return reviewRepository.save(reseña);
	    }
}
