package com.bibliomania.BiblioMania.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliomania.BiblioMania.model.BookCategory;
import com.bibliomania.BiblioMania.repository.BookCategoryRepository;

@Service
public class BookCategoryService {

    @Autowired
    private BookCategoryRepository repository;

    public BookCategory save(BookCategory bc) {
        return repository.save(bc);
    }

    public List<BookCategory> findByBookId(Long libroId) {
        return repository.findByLibroId(libroId);
    }

    public List<BookCategory> findByCategoryId(Long categoriaId) {
        return repository.findByCategoriaId(categoriaId);
    }
}
