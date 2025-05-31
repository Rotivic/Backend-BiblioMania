package com.bibliomania.BiblioMania.service;

import com.bibliomania.BiblioMania.dto.BookCategoryDTO;
import com.bibliomania.BiblioMania.dto.CategoryDTO;
import com.bibliomania.BiblioMania.exception.ResourceNotFoundException;
import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.BookCategory;
import com.bibliomania.BiblioMania.model.Category;
import com.bibliomania.BiblioMania.repository.BookCategoryRepository;
import com.bibliomania.BiblioMania.repository.BookRepository;
import com.bibliomania.BiblioMania.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    // Conversores
    private CategoryDTO convertirACategoryDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getNombre());
    }

    private Category convertirAEntidad(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setNombre(dto.getNombre());
        return category;
    }

    private BookCategoryDTO convertirABookCategoryDTO(BookCategory bc) {
        return new BookCategoryDTO(bc);
    }

    // CRUD Category
    public CategoryDTO createCategory(CategoryDTO dto) {
        Category saved = categoryRepository.save(convertirAEntidad(dto));
        return convertirACategoryDTO(saved);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertirACategoryDTO)
                .collect(Collectors.toList());
    }

    // Relación book-category
    public BookCategoryDTO assignCategoryToBook(BookCategoryDTO dto) {
        Book libro = bookRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + dto.getLibroId()));
        Category categoria = categoryRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + dto.getCategoriaId()));

        BookCategory bc = new BookCategory();
        bc.setLibro(libro);
        bc.setCategoria(categoria);

        return convertirABookCategoryDTO(bookCategoryRepository.save(bc));
    }

    public List<BookCategoryDTO> getCategoriesForBook(Long bookId) {
        return bookCategoryRepository.findByLibroId(bookId)
                .stream()
                .map(this::convertirABookCategoryDTO)
                .collect(Collectors.toList());
    }
}
