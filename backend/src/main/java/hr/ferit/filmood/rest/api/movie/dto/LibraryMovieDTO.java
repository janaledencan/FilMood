package hr.ferit.filmood.rest.api.movie.dto;

import java.util.List;

public record LibraryMovieDTO(String title,
                              List<String> genres,
                              Integer movieId,
                              Integer releaseYear,
                              Float voteAverage,
                              String posterPath,
                              Integer userRating) {
}
