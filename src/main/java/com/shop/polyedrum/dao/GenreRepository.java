package com.shop.polyedrum.dao;

import com.shop.polyedrum.domain.Category;
import com.shop.polyedrum.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
    List<Genre> getAllByCategory(Category category);
}
