package com.shop.polyedrum.service.impl;

import com.shop.polyedrum.dao.GenreRepository;

import com.shop.polyedrum.domain.Genre;
import com.shop.polyedrum.exception.ApiException;
import com.shop.polyedrum.exception.ResourceNotFoundException;
import com.shop.polyedrum.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }


    @Transactional
    @Override
    public String updateGenre(Genre genre, String name) {
        Genre exsGenre = genreRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "name", name));

        if (genreRepository.findByName(genre.getName()).isPresent()){
            throw new ApiException("genre with name " + genre.getName() + " already exists",
                    HttpStatus.BAD_REQUEST);
        }
        exsGenre.setName(genre.getName());
        return "genre updated successfully";
    }

    @Override
    public String createGenre(Genre genre) {
        if (genreRepository.findByName(genre.getName()).isPresent()){
            throw new ApiException("genre with name " + genre.getName() + " already exists",
                    HttpStatus.BAD_REQUEST);
        }
        genreRepository.save(genre);
        return "genre with name " + genre.getName() + " created successfully";
    }

    @Override
    public String deleteGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", id));
        genreRepository.deleteById(genre.getId());
        return "genre deleted successfully";
    }

    @Override
    public Genre getGenreByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "name", name));
    }
}
