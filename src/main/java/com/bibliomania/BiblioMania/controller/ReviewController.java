package com.bibliomania.BiblioMania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.service.ReviewService;
import com.bibliomania.BiblioMania.model.Review;
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	  @Autowired
	    private ReviewService reviewService;

	    @PostMapping("/publicar")
	    public ResponseEntity<Review> publicarReseña(@RequestBody Review review) {
	    	Review nuevaReseña = reviewService.publicarReseña(review);
	        return new ResponseEntity<>(nuevaReseña, HttpStatus.CREATED);
	    }
}
