package com.shop.polyedrum.service.impl;

import com.shop.polyedrum.dao.CategoryRepository;
import com.shop.polyedrum.dao.GenreRepository;
import com.shop.polyedrum.domain.Category;
import com.shop.polyedrum.domain.Genre;
import com.shop.polyedrum.exception.ApiException;
import com.shop.polyedrum.exception.ResourceNotFoundException;
import com.shop.polyedrum.service.CategoryService;
import com.shop.polyedrum.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final GenreService genreService;
    private final GenreRepository genreRepository;

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
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


    private void validateCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new ApiException("Category with name " + category.getName() + " is already exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String updateCategory(Category category, Long id) {
        Category exsCategory = getCategoryById(id);
        if (category.getName().equals(exsCategory.getName())){
            return "category updated";
        }
        validateCategory(category);
        exsCategory.setName(category.getName());
        categoryRepository.save(exsCategory);
        return "category updated";
    }

    @Override
    public String deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", id));
        genreRepository.getAllByCategory(category).forEach(el -> el.setCategory(null));
        category.setGenres(null);
        categoryRepository.deleteById(category.getId());
        return "category deleted successfully";
    }

    @Override
    public String addGenreToCategory(String categoryName, String genreName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));

        Genre genre = genreService.getGenreByName(genreName);

        if (genre.getCategory() != null && genre.getCategory().equals(category)) {
            throw new ApiException("genre " + genreName + " is already in the category " + categoryName, HttpStatus.BAD_REQUEST);
        }
        genre.setCategory(category);
        genreService.createGenre(genre);
        return "genre " + genreName + " added to " + categoryName;
    }

    @Transactional
    @Override
    public String deleteGenreFromCategory(String categoryName, String genreName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));



        Genre exsGenre = genreService.getGenreByName(genreName);

        for (Genre g : category.getGenres()) {
            if (g.getName().equals(genreName)) {
                exsGenre.setCategory(null);
            }
        }
        return "genre " + genreName + " was deleted from " + categoryName;
    }

    @Transactional
    @Override
    public String updateGenres(Long id, List<String> genreNames) {
        Category category = getCategoryById(id);
        genreRepository.getAllByCategory(category).forEach(el -> el.setCategory(null));

        List<Genre> genres = new ArrayList<>();
        for (String name : genreNames) {
            Genre genre = genreService.getGenreByName(name);
            genre.setCategory(category);
            genres.add(genre);
        }
        category.setGenres(genres);
        return "genres updated successfully!";
    }
}
