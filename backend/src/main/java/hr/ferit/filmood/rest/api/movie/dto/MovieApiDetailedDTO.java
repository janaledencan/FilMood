package hr.ferit.filmood.rest.api.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.ferit.filmood.rest.api.genre.dto.GenreDTO;

import java.util.List;

public record MovieApiDetailedDTO(Integer budget,
                                  List<GenreDTO> genres,
                                  @JsonProperty("imdb_id") String imdbId,
                                  @JsonProperty("id") Integer movieId,
                                  String overview,
                                  @JsonProperty("poster_path") String posterPath,
                                  @JsonProperty("release_date") String releaseDate,
                                  Integer revenue,
                                  Integer runtime,
                                  @JsonProperty("status") String releaseStatus,
                                  String title,
                                  String tagline,
                                  @JsonProperty("vote_average") Float voteAverage,
                                  @JsonProperty("vote_count") Integer voteCount
) {
}
