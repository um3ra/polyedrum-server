package com.shop.polyedrum.controller;

import com.shop.polyedrum.domain.Category;
import com.shop.polyedrum.dto.ResponseHandler;
import com.shop.polyedrum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("{name}")
    public ResponseEntity<Object> getCategoryByName(@PathVariable String name) {
        return ResponseHandler.responseBuilder("",
                HttpStatus.OK,
                categoryService.getCategoryByName(name));
    }

    @GetMapping
    public ResponseEntity<Object> getCategories() {
        return ResponseHandler.responseBuilder("",
                HttpStatus.OK,
                categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {
        return ResponseHandler.responseBuilder(categoryService.createCategory(category),
                HttpStatus.CREATED,
                "category created successfully");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        return ResponseHandler.responseBuilder(categoryService.deleteCategoryById(id), HttpStatus.OK, "");
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        return ResponseHandler.responseBuilder(
                "category updated successfully",
                HttpStatus.OK,
                categoryService.updateCategory(category, id));
    }

    @PostMapping("{category}")
    public ResponseEntity<Object> addGenreToCategory(@PathVariable String category, @RequestParam String genre) {
        return ResponseHandler.responseBuilder(
                categoryService.addGenreToCategory(category, genre),
                HttpStatus.OK,
                "");
    }

    @PutMapping("{category}/genre/{genre}")
    public ResponseEntity<Object> deleteGenreFromCategory(@PathVariable String category, @PathVariable String genre) {
        return ResponseHandler.responseBuilder(
                categoryService.deleteGenreFromCategory(category, genre),
                HttpStatus.OK,
                ""
        );
    }

    @PutMapping("{id}/genres")
    public ResponseEntity<Object> updateGenres(@PathVariable Long id, @RequestBody List<String> genreNames) {
        return ResponseHandler.responseBuilder(
                categoryService.updateGenres(id, genreNames),
                HttpStatus.OK,
                ""
        );
    }
}