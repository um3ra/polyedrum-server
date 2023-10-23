package com.shop.polyedrum.controller;

import com.shop.polyedrum.domain.Genre;
import com.shop.polyedrum.dto.ResponseHandler;
import com.shop.polyedrum.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(("api/v1/genres"))
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<Object> getGenres(){
        return ResponseHandler.responseBuilder("", HttpStatus.OK, genreService.getGenres());
    }

    @GetMapping("{name}")
    public ResponseEntity<Object> getGenresByName(@PathVariable String name){
        return ResponseHandler.responseBuilder("", HttpStatus.OK, genreService.getGenreByName(name));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createGenre(@RequestBody Genre genre){
        return ResponseHandler.responseBuilder(genreService.createGenre(genre), HttpStatus.OK, "");
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Object> deleteGenreByName(@PathVariable String name){
        return ResponseHandler.responseBuilder(genreService.deleteGenreByName(name),
                HttpStatus.OK,
                "");
    }

    @PutMapping("{name}")
    public ResponseEntity<Object> updateGenre(@RequestBody Genre genre, @PathVariable String name){
        return ResponseHandler.responseBuilder(genreService.updateGenre(genre, name),
                HttpStatus.OK,
                "");
    }

}
