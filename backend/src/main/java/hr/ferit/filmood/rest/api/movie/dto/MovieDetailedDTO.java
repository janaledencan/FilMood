package hr.ferit.filmood.rest.api.movie.dto;

import java.util.List;

public record MovieDetailedDTO(String title,
                               List<String> genres,
                               Integer movieId,
                               Integer releaseYear,
                               Float voteAverage,
                               String posterPath,
                               Integer budget,
                               String overview,
                               String imdbId,
                               Integer revenue,
                               Integer runtime,
                               String releaseStatus,
                               String tagline,
                               Integer voteCount,
                               Boolean isInMyLibrary) {
}
