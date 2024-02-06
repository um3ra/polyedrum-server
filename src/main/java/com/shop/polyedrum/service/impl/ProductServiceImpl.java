package com.shop.polyedrum.service.impl;

import com.shop.polyedrum.dao.GenreRepository;
import com.shop.polyedrum.dao.ProductInfoRepository;
import com.shop.polyedrum.dao.ProductRepository;
import com.shop.polyedrum.domain.Genre;
import com.shop.polyedrum.domain.Product;
import com.shop.polyedrum.domain.ProductInfo;
import com.shop.polyedrum.dto.AuthorDTO;
import com.shop.polyedrum.dto.PaginationResponse;
import com.shop.polyedrum.dto.ProductDTO;
import com.shop.polyedrum.exception.ApiException;
import com.shop.polyedrum.exception.ResourceNotFoundException;
import com.shop.polyedrum.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final GenreRepository genreRepository;
    private final ProductInfoRepository productInfoRepository;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Product", "id", id));
    }

    @Override
    public Product getProductByName(String name) {
        Product product = productRepository.findByTitle(name).orElseThrow(
                () -> new ResourceNotFoundException("Product", "name", name));
        return product;
    }

    @Transactional
    @Override
    public String createProduct(Product product) {
        validateProduct(product, "");

        ProductInfo productInfo = new ProductInfo();
        if (product.getProductInfo() != null){
            productInfo = product.getProductInfo();
        }
        product.setProductInfo(productInfo);
        productInfoRepository.save(product.getProductInfo());

        productRepository.save(product);
        return "product created successfully";
    }


    @Override
    public String updateProduct(Product product, Long id) {
        Product exsProduct = getProductById(id);
        validateProduct(product, exsProduct.getTitle());
        exsProduct.setPrice(product.getPrice());
        exsProduct.setTitle(product.getTitle());
        exsProduct.setAuthor(product.getAuthor());
        exsProduct.setProductInfo(product.getProductInfo());
        productRepository.save(exsProduct);
        return "product updated successfully";
    }

    private void validateProduct(Product product, String existingProductName){
        if (!product.getTitle().equals(existingProductName)
                && productRepository.findByTitle(product.getTitle()).isPresent())
        {
            throw new ApiException("Product with title " + product.getTitle() + " already exists",
                    HttpStatus.BAD_REQUEST);
        }
        if (product.getTitle() == null || product.getTitle().isEmpty()){
            throw new ApiException("the product name must be indicated",
                    HttpStatus.BAD_REQUEST);
        }
        if (product.getPrice() == null){
            throw new ApiException("the price of the product must be indicated",
                    HttpStatus.BAD_REQUEST);
        }
        if (product.getAuthor() == null || product.getAuthor().isEmpty()){
            product.setAuthor("unknown");
        }
    }

    private List<Product> sortProducts(String field, String sortOrder, Function<Sort, List<Product>> fun){
        if (field == null || field.isEmpty()){
            return fun.apply(Sort.by("title"));
        }
        Sort.Direction direction =
                sortOrder.equalsIgnoreCase("DESC") ?
                        Sort.Direction.DESC :
                        Sort.Direction.ASC;
        return fun.apply(Sort.by(direction, field));
    }

    private ProductDTO mapToDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .author(product.getAuthor())
                .price(product.getPrice())
                .img(product.getImageURL())
                .build();
    }

    @Override
    public PaginationResponse<ProductDTO> getAllProducts(int pageNo, int pageSize, String sortBy, String sortOrder){
        List<Product> sortedProducts = sortProducts(sortBy, sortOrder, productRepository::findAll);
        List<ProductDTO> productDTOS = sortedProducts
                .stream()
                .skip(pageNo * pageSize)
                .limit(pageSize)
                .map(this::mapToDTO).toList();
        return getResponseWithPagination(
                pageNo,
                pageSize,
                productDTOS,
                sortedProducts);
    }

    private PaginationResponse<ProductDTO> getResponseWithPagination(int pageNo, int pageSize, List<ProductDTO> productDTOS, List<Product> products){
        double totalPages = (double)products.size() / pageSize;
        PaginationResponse<ProductDTO> response = new PaginationResponse<>();
        response.setItems(productDTOS);
        response.setPageNo(pageNo);
        response.setTotalCount(products.size());
        response.setPageSize(pageSize);
        response.setTotalPages((int)Math.ceil(totalPages));
        return response;
    }

    @Override
    public String deleteProduct(Long id) {
        getProductById(id);
        productRepository.deleteById(id);
        return "product deleted successfully";
    }

    @Override
    public PaginationResponse<ProductDTO> findProductByGenre(int pageNo, int pageSize, String genreName, String sortBy, String sortOrder) {
        List<Product> filteredProducts = sortProducts(sortBy, sortOrder, productRepository::findAll).stream().filter(pr -> pr.getGenres()
                .stream().anyMatch(genre -> genre.getName().equals(genreName))).toList();
        List<ProductDTO> productDTOS = filteredProducts
                .stream()
                .skip(pageNo * pageSize)
                .limit(pageSize)
                .map(this::mapToDTO).toList();
        return getResponseWithPagination(
                pageNo,
                pageSize,
                productDTOS,
                filteredProducts);
    }

    @Transactional
    @Override
    public String addProductToGenre(String productName, String genreName) {
        Product product = productRepository.findByTitle(productName)
                .orElseThrow( () -> new ResourceNotFoundException("Product", "name", productName));

        Genre genre = genreRepository.findByName(genreName)
                .orElseThrow( () -> new ResourceNotFoundException("genre", "name", genreName));

        List<Genre> genres = product.getGenres();
        if (genres == null){
            genres = new ArrayList<>();
        }
        if (genres.stream().anyMatch(g -> g.equals(genre))){
            throw new ApiException(genre.getName() + " genre is already in the product " + product.getTitle(), HttpStatus.BAD_REQUEST);
        }


        genres.add(genre);
        product.setGenres(genres);
        return "product " + productName + " added to " + genreName;
    }

    @Override
    public PaginationResponse<ProductDTO> findProductsByAuthor(int pageNo, int pageSize, String author, String sortBy, String sortOrder) {
        List<Product> filteredProducts = sortProducts(sortBy, sortOrder, productRepository::findAll).stream()
                .filter( pr -> pr.getAuthor().equals(author)).toList();

        List<ProductDTO> productDTOS = filteredProducts.stream()
                .skip(pageNo * pageSize)
                .limit(pageSize)
                .map(this::mapToDTO).toList();

        return getResponseWithPagination(
                pageNo,
                pageSize,
                productDTOS,
                filteredProducts);
    }

    @Override
    public List<AuthorDTO> getAllProductAuthors(){
        long authorId = 1;
        List<String> authors = productRepository.findAllAuthors();
        List<AuthorDTO> authorDTOS = new ArrayList<>();
        for(String author : authors){
            authorDTOS.add(new AuthorDTO(authorId, author));
            authorId++;
        }
        return authorDTOS;
    }

    @Override
    public PaginationResponse<ProductDTO> getAllProductsByGenres(int pageNo, int pageSize, List<Genre> genres, String sortBy, String sortOrder){
        List<Product> sortedProducts = sortProducts(sortBy, sortOrder, (sort -> productRepository.findAllByGenresIn(genres, sort)));
        List<ProductDTO> productDTOS = sortedProducts.stream()
                .skip(pageNo * pageSize)
                .limit(pageSize)
                .map(this::mapToDTO).toList();

        return getResponseWithPagination(
                pageNo,
                pageSize,
                productDTOS,
                sortedProducts);
    }

    @Override
    public String deleteGenreFromProduct(String productName, String genreName) {
        Product product = productRepository.findByTitle(productName)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "title", productName));

        Genre genre = genreRepository.findByName(genreName)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "name", genreName));

        List<Genre> newGenres = product.getGenres()
                .stream()
                .filter(g -> !g.getName().equals(genre.getName()))
                .toList();

        product.setGenres(newGenres);
        return "genre with name " + genreName + " deleted";
    }
}