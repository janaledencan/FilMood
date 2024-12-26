package hr.ferit.filmood.rest.api.movie;

import hr.ferit.filmood.common.exception.response.ErrorResponse;
import hr.ferit.filmood.common.rest.PagedResponse;
import hr.ferit.filmood.rest.api.movie.dto.LibraryMovieDTO;
import hr.ferit.filmood.rest.api.movie.dto.MovieDetailedDTO;
import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import hr.ferit.filmood.rest.api.movie.request.RatingRequest;
import hr.ferit.filmood.rest.api.movie.response.MoviePagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

import static hr.ferit.filmood.common.CommonConstants.MOOD_REGEX;
import static hr.ferit.filmood.common.CommonConstants.MOVIE_LIST_REGEX;
import static hr.ferit.filmood.common.CommonConstants.MOVIE_PATH;

@RequestMapping(MOVIE_PATH)
@Validated
public interface MovieController {

    @Operation(
            summary = "Fetches movie list specified as request parameter.",
            description = "Fetches movie list specified as request parameter.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie list fetched successfully."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    )
            }
    )
    @GetMapping("/{list}")
    MoviePagedResponse getMovieList(@Parameter(description = "Movie list to fetch", example = "One of following values: top-rated, popular, now-playing, upcoming")
                                    @PathVariable(name = "list") @Pattern(regexp = MOVIE_LIST_REGEX) String movieList,
                                    @Parameter(description = "Page number to fetch, range: [1, 500]", example = "1")
                                    @NotNull @Min(1) @Max(500) @RequestParam Integer number, Authentication authentication);

    @Operation(
            summary = "Adds movie to library.",
            description = "Adds movie to library.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie added successfully."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    )
            }
    )
    @PostMapping("/library")
    void addToLibrary(@NotNull @RequestBody @Valid AddMovieToLibraryRequest addMovieToLibraryRequest, Authentication authentication);

    @Operation(
            summary = "Deletes movie from library by movie id.",
            description = "Deletes movie from library by movie id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie removed successfully."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    )
            }
    )
    @DeleteMapping("/library/{movie-id}")
    void removeFromLibrary(@Parameter(description = "Movie id of movie to remove, if movie with given movieId is not in library, 400 is returned", example = "104")
                           @PathVariable(name = "movie-id") Integer movieId, Authentication authentication);

    @Operation(
            summary = "Fetches movie details by movie id.",
            description = "Fetches movie details by movie id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie details fetched successfully."
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    )
            }
    )
    @GetMapping("/details/{movie-id}")
    MovieDetailedDTO getMovie(@Parameter(description = "Movie id of movie to fetch, if movie with given movieId doesn't exist, 404 is returned", example = "104")
                              @PathVariable(name = "movie-id") Integer movieId, Authentication authentication);

    @Operation(
            summary = "Fetches all movies inside User's library.",
            description = "Fetches all movies inside User's library.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movies from library fetched successfully."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    )
            }
    )
    @GetMapping("/library")
    PagedResponse<LibraryMovieDTO> getLibrary(@Parameter(description = "Specifies whether you want to fetch only rated movies (User rated them [1, 5]), not required, if wanted put to true", example = "true")
                                              @RequestParam(name = "rated-only", required = false) Boolean ratedOnly,
                                              @Parameter(description = "Page in range [1, totalNumber of pages returned in response]", example = "1")
                                              @RequestParam(name = "page", required = false) Integer page,
                                              @Parameter(description = "Represents wanted number of elements per page", example = "10")
                                              @RequestParam(name = "size", required = false) Integer size,
                                              @Parameter(description = "Parameter name by which to sort movies (suggested to use 'userRating' or 'createdAt' here)", example = "userRating")
                                              @RequestParam(name = "sort", required = false) String sort,
                                              @Parameter(description = "DESC (descending) or ASC (ascending)", example = "DESC")
                                              @RequestParam(name = "direction", required = false) String direction,
                                              @Parameter(description = "If not provided fetches all movies, if 0 fetches unrated movies, if any value between 1-5 fetches all movies which user rated with that rating", example = "0")
                                              @RequestParam(name = "user-rating", required = false) Integer userRating,
                                              Authentication authentication);

    @Operation(
            summary = "Rates a movie in library by movie id.",
            description = "Rates a movie in library by movie id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie rated successfully."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden.",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    )
            }
    )
    @PostMapping("/library/{movie-id}")
    void rate(@Parameter(description = "Movie id of movie in library to rate, if movie with given movieId is not in library, 400 is returned", example = "104")
              @PathVariable(name = "movie-id") Integer movieId, @RequestBody @NotNull @Valid RatingRequest ratingRequest, Authentication authentication);

    @Operation(
            summary = "Fetches movies recommended for specified mood.",
            description = "Fetches movies recommended for specified mood.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie list fetched successfully."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    )
            }
    )
    @GetMapping("/mood/{mood}")
    MoviePagedResponse getByMood(@Parameter(description = "Mood", example = "One of following values: sad, in-love, curious, motivated, brave, excited")
                                 @PathVariable(name = "mood") @Pattern(regexp = MOOD_REGEX) String mood,
                                 @Parameter(description = "Page number to fetch, range: [1, 500]", example = "1")
                                 @NotNull @Min(1) @Max(500) @RequestParam Integer number, Authentication authentication);
}
