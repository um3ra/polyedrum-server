package com.shop.polyedrum.dao;

import com.shop.polyedrum.domain.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByTitle(String name);
    @Query("select distinct p.author from Product p")
    List<String> findAllAuthors();
    List<Product> findAllByTitleContainingIgnoreCase(String search, Sort sort);
    List<Product> findAllByTitleContainingIgnoreCaseAndGenres_NameIn(String destination, List<String> genreNames, Sort sort);
}