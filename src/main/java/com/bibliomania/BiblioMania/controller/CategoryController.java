package com.bibliomania.BiblioMania.controller;

import com.bibliomania.BiblioMania.dto.BookCategoryDTO;
import com.bibliomania.BiblioMania.dto.CategoryDTO;
import com.bibliomania.BiblioMania.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Crear categoría
    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    // Obtener todas las categorías
    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Asignar categoría a un libro
    @PostMapping("/assign")
    public BookCategoryDTO assignCategoryToBook(@RequestBody BookCategoryDTO bookCategoryDTO) {
        return categoryService.assignCategoryToBook(bookCategoryDTO);
    }

    // Obtener categorías de un libro
    @GetMapping("/book/{bookId}")
    public List<BookCategoryDTO> getCategoriesForBook(@PathVariable Long bookId) {
        return categoryService.getCategoriesForBook(bookId);
    }
}
