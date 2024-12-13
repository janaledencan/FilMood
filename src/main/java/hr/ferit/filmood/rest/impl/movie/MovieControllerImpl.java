package hr.ferit.filmood.rest.impl.movie;

import hr.ferit.filmood.rest.api.movie.MovieController;
import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import hr.ferit.filmood.rest.api.movie.response.MoviePagedResponse;
import hr.ferit.filmood.service.MovieService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieControllerImpl implements MovieController {

    private final MovieService movieService;

    public MovieControllerImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public MoviePagedResponse getMovieList(String movieList, Integer number, Authentication authentication) {
        return movieService.getMovieList(movieList, number, authentication);
    }

    @Override
    public void addToLibrary(AddMovieToLibraryRequest addMovieToLibraryRequest, Authentication authentication) {
        movieService.addToLibrary(addMovieToLibraryRequest, authentication);
    }

    @Override
    public void removeFromLibrary(Integer movieId, Authentication authentication) {
        movieService.removeFromLibrary(movieId, authentication);
    }
}
