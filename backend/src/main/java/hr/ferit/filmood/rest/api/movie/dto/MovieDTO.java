package hr.ferit.filmood.rest.api.movie.dto;

import java.util.List;

public record MovieDTO(String title,
                       List<String> genres,
                       Integer movieId,
                       Integer releaseYear,
                       Float voteAverage,
                       String posterPath,
                       Boolean isInMyLibrary) {
}
