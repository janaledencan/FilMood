package hr.ferit.filmood.service;

import hr.ferit.filmood.rest.api.movie.response.MoviePagedResponse;
import org.springframework.security.core.Authentication;

public interface MovieService {

    MoviePagedResponse getMovieList(String movieList, Integer number, Authentication authentication);
}
