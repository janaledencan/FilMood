package hr.ferit.filmood.rest.api.movie.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AddMovieToLibraryRequest(@NotBlank String title,
                                       List<String> genres,
                                       @NotNull Integer movieId,
                                       @NotNull Integer releaseYear,
                                       @NotNull Float voteAverage,
                                       String posterPath) {

    @JsonCreator
    public AddMovieToLibraryRequest {

    }
}
