package hr.ferit.filmood.rest.api.movie;

import hr.ferit.filmood.common.rest.PagedResponse;
import hr.ferit.filmood.common.rest.movie.LibraryPageQuery;
import hr.ferit.filmood.rest.api.movie.dto.LibraryMovieDTO;
import hr.ferit.filmood.rest.api.movie.dto.MovieDetailedDTO;
import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import hr.ferit.filmood.rest.api.movie.request.RatingRequest;
import hr.ferit.filmood.rest.api.movie.response.MoviePagedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static hr.ferit.filmood.common.CommonConstants.MOVIE_LIST_REGEX;
import static hr.ferit.filmood.common.CommonConstants.MOVIE_PATH;

@RequestMapping(MOVIE_PATH)
@Validated
public interface MovieController {

    @GetMapping("/{list}")
    MoviePagedResponse getMovieList(@PathVariable(name = "list") @Pattern(regexp = MOVIE_LIST_REGEX) String movieList,
                                    @NotNull @Min(1) @Max(500) @RequestParam Integer number, Authentication authentication);

    @PostMapping("/library")
    void addToLibrary(@NotNull @RequestBody @Valid AddMovieToLibraryRequest addMovieToLibraryRequest, Authentication authentication);

    @DeleteMapping("/library/{movie-id}")
    void removeFromLibrary(@PathVariable(name = "movie-id") Integer movieId, Authentication authentication);

    @GetMapping("/details/{movie-id}")
    MovieDetailedDTO getMovie(@PathVariable(name = "movie-id") Integer movieId, Authentication authentication);

    @GetMapping("/library")
    PagedResponse<LibraryMovieDTO> getLibrary(@RequestParam(name = "rated-only", required = false) Boolean ratedOnly,
                                              @RequestBody @NotNull @Valid LibraryPageQuery query,
                                              Authentication authentication);

    @PostMapping("/library/{movie-id}")
    void rate(@PathVariable(name = "movie-id") Integer movieId, @RequestBody @NotNull @Valid RatingRequest ratingRequest, Authentication authentication);
}
