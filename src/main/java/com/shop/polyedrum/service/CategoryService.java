package com.shop.polyedrum.service;

import com.shop.polyedrum.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryByName(String categoryName);
    String createCategory(Category category);
    String updateCategory(Category category, String categoryName);
    String deleteCategoryByName(String categoryName);
    String addGenreToCategory(String categoryName, String genreName);
    String deleteGenreFromCategory(String categoryName, String genreName);
}
