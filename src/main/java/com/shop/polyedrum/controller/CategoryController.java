package com.shop.polyedrum.controller;

import com.shop.polyedrum.domain.Category;
import com.shop.polyedrum.dto.ResponseHandler;
import com.shop.polyedrum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("{name}")
    public ResponseEntity<Object> getCategoryByName(@PathVariable String name){
        return ResponseHandler.responseBuilder("",
                HttpStatus.OK,
                categoryService.getCategoryByName(name));
    }

    @GetMapping
    public ResponseEntity<Object> getCategories(){
        return ResponseHandler.responseBuilder("",
                HttpStatus.OK,
                categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody Category category){
        return ResponseHandler.responseBuilder(categoryService.createCategory(category),
                HttpStatus.CREATED,
                "category created successfully");
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Object> deleteCategoryById(@PathVariable String name){
        return ResponseHandler.responseBuilder(categoryService.deleteCategoryByName(name), HttpStatus.OK, "");
    }

    @PutMapping("{name}")
    public ResponseEntity<Object> updateCategoryByName(@RequestBody Category category, @PathVariable String name){
        return ResponseHandler.responseBuilder(
                "category updated successfully",
                HttpStatus.OK,
                categoryService.updateCategory(category, name));
    }

    @PostMapping("{category}")
    public ResponseEntity<Object> addGenreToCategory(@PathVariable String category, @RequestParam String genre){
        return ResponseHandler.responseBuilder(
                categoryService.addGenreToCategory(category, genre),
                HttpStatus.OK,
                "");
    }

    @PutMapping("{category}/genre/{genre}")
    public ResponseEntity<Object> deleteGenreFromCategory(@PathVariable String category, @PathVariable String genre){
        return ResponseHandler.responseBuilder(
                categoryService.deleteGenreFromCategory(category, genre),
                HttpStatus.OK,
                ""
        );
    }
}