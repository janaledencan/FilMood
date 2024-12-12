package hr.ferit.filmood.rest.api.movie.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.ferit.filmood.rest.api.movie.dto.MovieApiDTO;

import java.util.List;

public record MovieApiPagedResponse(@JsonProperty("page") Integer number,
                                    @JsonProperty("total_pages") Integer totalPages,
                                    @JsonProperty("total_results") Long totalElements,
                                    @JsonProperty("results") List<MovieApiDTO> content) {
}
