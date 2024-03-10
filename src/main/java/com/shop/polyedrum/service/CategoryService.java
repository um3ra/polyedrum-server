package com.shop.polyedrum.service;

import com.shop.polyedrum.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category getCategoryByName(String categoryName);
    String createCategory(Category category);
    String updateCategory(Category category, Long id);
    String deleteCategoryById(Long id);
    String addGenreToCategory(String categoryName, String genreName);
    String deleteGenreFromCategory(String categoryName, String genreName);
    String updateGenres(Long id, List<String> genreNames);
}
