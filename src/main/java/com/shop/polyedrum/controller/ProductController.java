package com.shop.polyedrum.controller;

import com.shop.polyedrum.domain.Category;
import com.shop.polyedrum.domain.Genre;
import com.shop.polyedrum.domain.Product;
import com.shop.polyedrum.dto.ResponseHandler;
import com.shop.polyedrum.exception.ApiException;
import com.shop.polyedrum.service.CategoryService;
import com.shop.polyedrum.service.ProductService;
import com.shop.polyedrum.util.ImageData;
import com.shop.polyedrum.util.ImageDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ImageDataService imageDataService;
    private final CategoryService categoryService;

    @Value("${app.base_url}")
    private String path;

    @GetMapping
    ResponseEntity<Object> getProducts(@RequestParam(required = false) String sort,
                                       @RequestParam(required = false, defaultValue = "asc") String order,
                                       @RequestParam(required = false, defaultValue = "0") int pageNo,
                                       @RequestParam(required = false, defaultValue = "10") int pageSize,
                                       @RequestParam(required = false) String destination
    ) {
        return ResponseHandler.responseBuilder("", HttpStatus.OK, productService.getAllProducts(pageNo, pageSize, sort, order, destination));
    }

    @GetMapping("/genre/{genreName}")
    public ResponseEntity<Object> getProductByGenreName(@PathVariable String genreName,
                                                        @RequestParam(required = false, defaultValue = "0") int pageNo,
                                                        @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                        @RequestParam(required = false) String sort,
                                                        @RequestParam(required = false, defaultValue = "asc") String order,
                                                        @RequestParam(required = false) String destination
    ) {
        List<String> genreNames = new ArrayList<>();
        genreNames.add(genreName);
        return ResponseHandler.responseBuilder("",
                HttpStatus.OK, productService.getAllProductsByGenreNames(pageNo, pageSize, sort, order, destination, genreNames));
    }

    @GetMapping({"{name}"})
    public ResponseEntity<Object> getProductByName(@PathVariable String name) {
        return ResponseHandler.responseBuilder("", HttpStatus.OK, productService.getProductByName(name));
    }

    @GetMapping("/img/{fileName}")
    public ResponseEntity<Object> downloadProductImage(@PathVariable String fileName) throws IOException {
        if (fileName.endsWith("png") || fileName.endsWith("jpg")) {
            byte[] imageData = imageDataService.downloadImage(fileName);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
        }
        throw new ApiException("format is incorrect", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/author/{authorName}")
    public ResponseEntity<Object> getProductByAuthorName(@PathVariable String authorName,
                                                         @RequestParam(required = false, defaultValue = "0") int pageNo,
                                                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                         @RequestParam(required = false) String sort,
                                                         @RequestParam(required = false, defaultValue = "asc") String order
    ) {
        return ResponseHandler.responseBuilder("",
                HttpStatus.OK, productService.findProductsByAuthor(pageNo, pageSize, authorName, sort, order));
    }

    @GetMapping("/author/all")
    public ResponseEntity<Object> getAllProductAuthors() {
        return ResponseHandler.responseBuilder("", HttpStatus.OK, productService.getAllProductAuthors());
    }

    @GetMapping("category/{name}")
    public ResponseEntity<Object> getAllProductsByCategory(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                                           @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                           @RequestParam(required = false) String sort,
                                                           @RequestParam(required = false, defaultValue = "asc") String order,
                                                           @RequestParam(required = false) String destination,
                                                           @PathVariable String name

    ) {
        Category category = categoryService.getCategoryByName(name);
        List<String> genres = category.getGenres().stream().map(Genre::getName).toList();
        return ResponseHandler.responseBuilder("", HttpStatus.OK, productService.getAllProductsByGenreNames(pageNo, pageSize, sort, order, destination, genres));
    }


    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        return ResponseHandler.responseBuilder(productService.createProduct(product), HttpStatus.CREATED, "");
    }


    @PutMapping("{id}/genres")
    public ResponseEntity<Object> updateGenres(@PathVariable Long id, @RequestBody List<String> genres){
        return ResponseHandler.responseBuilder(productService.updateGenres(id, genres), HttpStatus.OK, "");
    }


    @PostMapping
    public ResponseEntity<Object> uploadImageToProduct(@RequestParam MultipartFile image, @RequestParam String product) throws IOException {
        Product pr = productService.getProductByName(product);
        ImageData uploadImage = imageDataService.uploadImage(image);
        pr.setImageURL(path + "/api/v1/products/img/" + image.getOriginalFilename());
        pr.setImage(uploadImage);
        productService.updateProduct(pr, pr.getId());
        return ResponseHandler.responseBuilder("image uploaded successfully", HttpStatus.OK, "");
    }

    @PostMapping("{product}")
    public ResponseEntity<Object> addProductToGenre(@PathVariable String product, @RequestParam String genre) {
        return ResponseHandler.responseBuilder(productService.addProductToGenre(product, genre),
                HttpStatus.OK, "");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        return ResponseHandler.responseBuilder(productService.updateProduct(product, id),
                HttpStatus.OK,
                "");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        return ResponseHandler.responseBuilder(productService.deleteProduct(id), HttpStatus.OK, "");
    }


    @PutMapping("{product}/genre/{genre}")
    public ResponseEntity<Object> deleteGenreFromProduct(@PathVariable String product, @PathVariable String genre) {
        return ResponseHandler.responseBuilder(productService.deleteGenreFromProduct(product, genre), HttpStatus.OK, "");
    }
}