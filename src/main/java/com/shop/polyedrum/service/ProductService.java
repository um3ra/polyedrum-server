package com.shop.polyedrum.service;

import com.shop.polyedrum.domain.Genre;
import com.shop.polyedrum.domain.Product;
import com.shop.polyedrum.dto.AuthorDTO;
import com.shop.polyedrum.dto.PaginationResponse;
import com.shop.polyedrum.dto.ProductDTO;

import java.util.List;


public interface ProductService {
    Product getProductById(Long id);
    Product getProductByName(String name);
    String createProduct(Product product);
    String updateProduct(Product product, Long id);
    String deleteProduct(Long id);
    String deleteGenreFromProduct(String productName, String genreName);
    String addProductToGenre(String productName, String genreName);
    PaginationResponse<ProductDTO> getAllProducts(int pageNo, int PageSize, String sortBy, String sortOrder);
    PaginationResponse<ProductDTO> findProductByGenre(int pageNo, int PageSize, String categoryName, String sortBy, String sortOrder);
    PaginationResponse<ProductDTO> findProductsByAuthor(int pageNo, int PageSize, String author, String sortBy, String sortOrder);
    List<AuthorDTO> getAllProductAuthors();
    PaginationResponse<ProductDTO> getAllProductsByGenres(int pageNo, int PageSize, List<Genre> genres, String sortBy, String sortOrder);
}
