package hr.ferit.filmood.rest.api.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MovieDTO(String title,
                       @JsonProperty("genre_ids") List<Integer> genres,
                       @JsonProperty("id") Integer movieId,
                       @JsonProperty("release_date") String releaseDate,
                       @JsonProperty("vote_average") Float voteAverage,
                       @JsonProperty("poster_path") String posterPath) {
}
