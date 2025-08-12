package com.movieflix.controller;

import com.movieflix.entity.Movie;
import com.movieflix.mapper.MovieMapper;
import com.movieflix.request.MovieRequest;
import com.movieflix.response.MovieResponse;
import com.movieflix.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/movie")
@RequiredArgsConstructor
@Tag(name= "Movie", description = "Responsavel pelo gerenciamento dos filmes")
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

    @Operation(summary = "Salvar filme", description = "Metodo responsável por salvar filmes"
    ,security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Filme salvo", content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @PostMapping
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody MovieRequest movie) {
        Movie savedMovie = movieService.save(MovieMapper.toMovie(movie));
        return ResponseEntity.ok(MovieMapper.toMovieReponse(savedMovie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable long id, @Valid @RequestBody MovieRequest updatedMovie) {
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
