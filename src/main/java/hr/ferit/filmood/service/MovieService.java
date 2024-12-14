package hr.ferit.filmood.service;

import hr.ferit.filmood.common.rest.PagedResponse;
import hr.ferit.filmood.common.rest.movie.LibraryPageQuery;
import hr.ferit.filmood.rest.api.movie.dto.LibraryMovieDTO;
import hr.ferit.filmood.rest.api.movie.dto.MovieDetailedDTO;
import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import hr.ferit.filmood.rest.api.movie.request.RatingRequest;
import hr.ferit.filmood.rest.api.movie.response.MoviePagedResponse;
import org.springframework.security.core.Authentication;

public interface MovieService {

    MoviePagedResponse getMovieList(String movieList, Integer number, Authentication authentication);

    void addToLibrary(AddMovieToLibraryRequest addMovieToLibraryRequest, Authentication authentication);

    void removeFromLibrary(Integer movieId, Authentication authentication);

    MovieDetailedDTO getMovie(Integer movieId, Authentication authentication);

    PagedResponse<LibraryMovieDTO> getLibrary(Boolean ratedOnly, LibraryPageQuery query, Authentication authentication);

    void rate(Integer movieId, RatingRequest ratingRequest, Authentication authentication);
}
