package com.shop.polyedrum.service.impl;

import com.shop.polyedrum.dao.CategoryRepository;
import com.shop.polyedrum.dao.GenreRepository;
import com.shop.polyedrum.domain.Category;
import com.shop.polyedrum.domain.Genre;
import com.shop.polyedrum.exception.ApiException;
import com.shop.polyedrum.exception.ResourceNotFoundException;
import com.shop.polyedrum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final GenreRepository genreRepository;

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
    }

    @Override
    public String createCategory(Category category) {
        validateCategory(category);
        categoryRepository.save(category);
        return "Category was created";
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    private void validateCategory(Category category){
        if (categoryRepository.findByName(category.getName()).isPresent()){
            throw new ApiException("Category with name " + category.getName() + " is already exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String updateCategory(Category category, String categoryName) {
        validateCategory(category);
        Category exsCategory = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
        exsCategory.setName(category.getName());
        categoryRepository.save(exsCategory);
        return "category updated";
    }

    @Override
    public String deleteCategoryByName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
        categoryRepository.deleteById(category.getId());
        return "category deleted successfully";
    }

    @Override
    public String addGenreToCategory(String categoryName, String genreName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
        Genre genre = genreRepository.findByName(genreName)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "name", genreName));

        if (genre.getCategory() != null && genre.getCategory().equals(category)){
            throw new ApiException("genre " + genreName + " is already in the category " + categoryName, HttpStatus.BAD_REQUEST);
        }
        genre.setCategory(category);
        genreRepository.save(genre);
        return "genre " + genreName + " added to " + categoryName;
    }

    @Override
    public String deleteGenreFromCategory(String categoryName, String genreName){
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));

        Genre exsGenre = genreRepository.findByName(genreName)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "name", genreName));

        for(Genre g : category.getGenres()){
            if (g.getName().equals(genreName)){
                exsGenre.setCategory(null);
            }
        }
        return "genre " + genreName + " was deleted from " + categoryName;
    }
}
