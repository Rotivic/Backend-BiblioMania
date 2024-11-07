package com.bibliomania.BiblioMania.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliomania.BiblioMania.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
