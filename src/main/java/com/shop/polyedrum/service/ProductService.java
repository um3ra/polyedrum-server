package com.shop.polyedrum.service;

import com.shop.polyedrum.domain.Product;
import com.shop.polyedrum.dto.AuthorDTO;
import com.shop.polyedrum.dto.PaginationResponse;
import com.shop.polyedrum.dto.ProductDTO;

import java.util.List;


public interface ProductService {
    Product getProductById(Long id);
    Product getProductByName(String name);
    PaginationResponse<ProductDTO> getAllProductsByGenreNames(int pageNo, int pageSize, String sortBy, String sortOrder, String destination, List<String> genres);
    PaginationResponse<ProductDTO> findProductsByAuthor(int pageNo, int PageSize, String author, String sortBy, String sortOrder);
    PaginationResponse<ProductDTO> getAllProducts(int pageNo, int PageSize, String sortBy, String sortOrder, String destination);
    List<AuthorDTO> getAllProductAuthors();
    String createProduct(Product product);
    String updateProduct(Product product, Long id);
    String deleteProduct(Long id);
    String deleteGenreFromProduct(String productName, String genreName);
    String addProductToGenre(String productName, String genreName);
}