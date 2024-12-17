package hr.ferit.filmood.rest.api.movie.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingRequest(@NotNull @Min(0) @Max(5) Integer userRating) {

    @JsonCreator
    public RatingRequest {

    }
}
