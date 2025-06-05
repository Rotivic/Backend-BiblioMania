package com.bibliomania.BiblioMania.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.dto.UserStatisticsDTO;
import com.bibliomania.BiblioMania.repository.ActividadLecturaRepository;
import com.bibliomania.BiblioMania.repository.BookCategoryRepository;
import com.bibliomania.BiblioMania.repository.CategoryRepository;
import com.bibliomania.BiblioMania.repository.LibroLeidoRepository;
import com.bibliomania.BiblioMania.repository.ListaRepository;

@Service
public class StatisticsService {

    @Autowired
    private LibroLeidoRepository leidoRepo;

    @Autowired
    private ListaRepository listaRepo;

    @Autowired
    private BookCategoryRepository bookCategoryRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ActividadLecturaRepository actividadRepo;

    
    public UserStatisticsDTO getUserStatistics(Long userId) {
        int totalReadBooks = leidoRepo.countByUsuarioId(userId);
        int totalListsCreated = listaRepo.countByUsuarioId(userId);
        Integer totalMinutos = actividadRepo.totalMinutosPorUsuario(userId);
        
        if (totalMinutos == null) totalMinutos = 0;
        
        // Lecturas por mes
        List<Object[]> booksPerMonth = leidoRepo.countBooksReadPerMonth(userId);
        Map<String, Integer> booksReadPerMonth = new LinkedHashMap<>();
        String[] monthNames = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        for (Object[] row : booksPerMonth) {
            int month = ((Number) row[0]).intValue();
            int count = ((Number) row[1]).intValue();
            booksReadPerMonth.put(monthNames[month - 1], count);
        }

        // Categorías consumidas
        List<Object[]> categories = bookCategoryRepo.findCategoryUsageByUserId(userId);
        Map<String, Integer> categoriesConsumed = new HashMap<>();
        for (Object[] row : categories) {
            Long categoryId = ((Number) row[0]).longValue();
            int count = ((Number) row[1]).intValue();
            String categoryName = categoryRepo.findById(categoryId)
                                              .map(c -> c.getNombre())
                                              .orElse("Desconocida");
            categoriesConsumed.put(categoryName, count);
        }

        return new UserStatisticsDTO(
            totalReadBooks,
            totalListsCreated,
            booksReadPerMonth,
            categoriesConsumed,
            totalMinutos
        );
    }
}
