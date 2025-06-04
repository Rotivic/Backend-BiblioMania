package com.bibliomania.BiblioMania.controller;

import java.util.List;
import java.util.NoSuchElementException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.dto.BookCategoryDTO;
import com.bibliomania.BiblioMania.dto.CategoryDTO;
import com.bibliomania.BiblioMania.model.Category;
import com.bibliomania.BiblioMania.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/assign")
    public BookCategoryDTO assignCategoryToBook(@RequestBody BookCategoryDTO categoryDTO) {
        return categoryService.assignCategoryToBookByIsbn(categoryDTO);
    }

    @GetMapping("/book/{isbn}")
    public List<CategoryDTO> getCategoriesForBook(@PathVariable String isbn) {
        return categoryService.getCategoriesForBookByIsbn(isbn);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryName(@PathVariable Long id, @RequestBody Category updatedCategory) {
        try {
            Category updated = categoryService.updateCategoryName(id, updatedCategory.getNombre());
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada.");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeCategoryFromBook(@RequestParam String isbn, @RequestParam Long categoryId) {
        categoryService.removeCategoryFromBook(isbn, categoryId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada.");
        }
    }

    
}
