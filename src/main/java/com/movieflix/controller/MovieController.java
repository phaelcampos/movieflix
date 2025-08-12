package com.movieflix.controller;

import com.movieflix.entity.Movie;
import com.movieflix.mapper.MovieMapper;
import com.movieflix.request.MovieRequest;
import com.movieflix.response.MovieResponse;
import com.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.ok(movieService.findAll().stream()
                .map(MovieMapper::toMovieReponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovie(@PathVariable long id) {
        return movieService.findById(id)
                .map(movie -> ResponseEntity.ok(
                        MovieMapper.toMovieReponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> findByCategory(@RequestParam long categoryId) {
        List<Movie> byCategoryId = movieService.findByCategoryId(categoryId);
        return ResponseEntity.ok(byCategoryId.stream()
                .map(MovieMapper::toMovieReponse)
                .toList());
    }

    @PostMapping
    public ResponseEntity<MovieResponse> addMovie(@RequestBody MovieRequest movie) {
        Movie savedMovie = movieService.save(MovieMapper.toMovie(movie));
        return ResponseEntity.ok(MovieMapper.toMovieReponse(savedMovie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable long id, @RequestBody MovieRequest updatedMovie) {
        return movieService.update(id, MovieMapper.toMovie(updatedMovie))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieReponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieResponse> deleteMovie(@PathVariable long id) {
        movieService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
