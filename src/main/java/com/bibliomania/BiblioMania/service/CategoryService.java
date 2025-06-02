package com.bibliomania.BiblioMania.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.dto.BookCategoryDTO;
import com.bibliomania.BiblioMania.dto.CategoryDTO;
import com.bibliomania.BiblioMania.exception.ResourceNotFoundException;
import com.bibliomania.BiblioMania.model.Book;
import com.bibliomania.BiblioMania.model.BookCategory;
import com.bibliomania.BiblioMania.model.Category;
import com.bibliomania.BiblioMania.repository.BookCategoryRepository;
import com.bibliomania.BiblioMania.repository.BookRepository;
import com.bibliomania.BiblioMania.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

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

    // NUEVO: Usando ISBN
    public BookCategoryDTO assignCategoryToBookByIsbn(BookCategoryDTO dto) {
        Book libro = bookRepository.findByIsbn(dto.getLibroIsbn())
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ISBN: " + dto.getLibroIsbn()));
        Category categoria = categoryRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + dto.getCategoriaId()));

        BookCategory bc = new BookCategory();
        bc.setLibro(libro);
        bc.setCategoria(categoria);

        return convertirABookCategoryDTO(bookCategoryRepository.save(bc));
    }

    // NUEVO: Usando ISBN
    public List<CategoryDTO> getCategoriesForBookByIsbn(String isbn) {
        Book libro = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ISBN: " + isbn));

        return bookCategoryRepository.findByLibroId(libro.getId())
                .stream()
                .map(BookCategory::getCategoria)
                .map(this::convertirACategoryDTO)
                .collect(Collectors.toList());
    }

    public Category updateCategoryName(Long id, String newName) {
        Category cat = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        
        cat.setNombre(newName);
        return categoryRepository.save(cat);
    }
    
    public void removeCategoryFromBook(String isbn, Long categoryId) {
        Book libro = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ISBN: " + isbn));
        Category categoria = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + categoryId));

        Optional<BookCategory> bookCategoryOpt = bookCategoryRepository.findByLibroAndCategoria(libro, categoria);

        if (bookCategoryOpt.isPresent()) {
            bookCategoryRepository.delete(bookCategoryOpt.get());
        } else {
            throw new ResourceNotFoundException("Relación libro-categoría no encontrada");
        }
    }
    
}
