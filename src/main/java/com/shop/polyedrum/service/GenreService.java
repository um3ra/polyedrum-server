package com.shop.polyedrum.service;

import com.shop.polyedrum.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getGenres();
    Genre getGenreByName(String name);
    String updateGenre(Genre genre, String name);
    String createGenre(Genre genre);
    String deleteGenreByName(String name);
}
