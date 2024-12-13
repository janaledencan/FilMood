package hr.ferit.filmood.service;

import hr.ferit.filmood.rest.api.movie.dto.MovieDetailedDTO;
import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import hr.ferit.filmood.rest.api.movie.response.MoviePagedResponse;
import org.springframework.security.core.Authentication;

public interface MovieService {

    MoviePagedResponse getMovieList(String movieList, Integer number, Authentication authentication);

    void addToLibrary(AddMovieToLibraryRequest addMovieToLibraryRequest, Authentication authentication);

    void removeFromLibrary(Integer movieId, Authentication authentication);

    MovieDetailedDTO getMovie(Integer movieId, Authentication authentication);
}
