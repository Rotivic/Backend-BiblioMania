package com.bibliomania.BiblioMania.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.dto.LibroDTO;
import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.service.RecommendationService;

@RestController
@RequestMapping("api/recomendaciones")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/por-categorias/{userId}")
    public List<LibroDTO> getRecommendedByCategory(@PathVariable Long userId) {
        return recommendationService.getRecommendedBooksByCategory(userId);
    }

    @GetMapping("/mejor-valorados")
    public List<LibroDTO> getTopRatedBooks(@RequestParam(defaultValue = "10") int limit) {
        return recommendationService.getTopRatedBooks(limit);
    }
}
