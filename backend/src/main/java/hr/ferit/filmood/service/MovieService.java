package hr.ferit.filmood.service;

import hr.ferit.filmood.rest.api.movie.dto.MovieDetailedDTO;
import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import hr.ferit.filmood.rest.api.movie.request.RatingRequest;
import hr.ferit.filmood.rest.api.movie.response.LibraryPagedResponse;
import hr.ferit.filmood.rest.api.movie.response.MoviePagedResponse;
import org.springframework.security.core.Authentication;

public interface MovieService {

    MoviePagedResponse getMovieList(String movieList, Integer number, Authentication authentication);

    void addToLibrary(AddMovieToLibraryRequest addMovieToLibraryRequest, Authentication authentication);

    void removeFromLibrary(Integer movieId, Authentication authentication);

    MovieDetailedDTO getMovie(Integer movieId, Authentication authentication);

    LibraryPagedResponse getLibrary(Boolean ratedOnly, Integer page, Integer size, String sort, String direction, Integer userRating, Authentication authentication);

    void rate(Integer movieId, RatingRequest ratingRequest, Authentication authentication);

    MoviePagedResponse getByMood(String mood, Integer number, Authentication authentication);
}
