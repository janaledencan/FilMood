package hr.ferit.filmood.rest.api.movie.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static hr.ferit.filmood.common.CommonConstants.MOVIE_LIST_REGEX;

public record MovieListRequest(@NotBlank @Pattern(regexp = MOVIE_LIST_REGEX) String movieList) {

    @JsonCreator
    public MovieListRequest {
    }
}
