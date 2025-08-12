package com.movieflix.mapper;

import com.movieflix.entity.Category;
import com.movieflix.entity.Movie;
import com.movieflix.entity.Streaming;
import com.movieflix.request.MovieRequest;
import com.movieflix.response.CategoryResponse;
import com.movieflix.response.MovieResponse;
import com.movieflix.response.StreamingResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MovieMapper {

    public static Movie toMovie(MovieRequest request) {

        List<Category> categoriesList = request.categories().stream()
                .map(categoryId -> Category.builder()
                        .id(categoryId)
                        .build())
                .toList();

        List<Streaming> streamingList = request.streamings().stream()
                .map(streamingId -> Streaming.builder()
                        .id(streamingId)
                        .build())
                .toList();

        return Movie.builder()
                .title(request.title())
                .description(request.description())
                .rating(request.rating())
                .releaseDate(request.releaseDate())
                .streamings(streamingList)
                .categories(categoriesList)
                .build();
    }

    public static MovieResponse toMovieReponse(Movie movie) {
        List<CategoryResponse> categoryList = movie.getCategories()
                .stream()
                .map(category -> CategoryMapper.toCategoryResponse(category))
                .toList();

        List<StreamingResponse> streamingResponseList = movie.getStreamings()
                .stream()
                .map(streaming -> StreamingMapper.toStreamingResponse(streaming))
                .toList();

        return MovieResponse.builder()
                .title(movie.getTitle())
                .description(movie.getDescription())
                .rating(movie.getRating())
                .releaseDate(movie.getReleaseDate())
                .categories(categoryList)
                .streamings(streamingResponseList)
                .updatedAt(movie.getUpdatedAt())
                .createdAt(movie.getCreatedAt())
                .id(movie.getId())
                .build();

    }
}
