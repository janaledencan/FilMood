package hr.ferit.filmood.rest.impl.movie;

import hr.ferit.filmood.common.rest.PagedResponse;
import hr.ferit.filmood.common.rest.movie.LibraryPageQuery;
import hr.ferit.filmood.rest.api.movie.MovieController;
import hr.ferit.filmood.rest.api.movie.dto.LibraryMovieDTO;
import hr.ferit.filmood.rest.api.movie.dto.MovieDetailedDTO;
import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import hr.ferit.filmood.rest.api.movie.request.RatingRequest;
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

    @Override
    public MovieDetailedDTO getMovie(Integer movieId, Authentication authentication) {
        return movieService.getMovie(movieId, authentication);
    }

    @Override
    public PagedResponse<LibraryMovieDTO> getLibrary(Boolean ratedOnly, LibraryPageQuery query, Authentication authentication) {
        return movieService.getLibrary(ratedOnly, query, authentication);
    }

    @Override
    public void rate(Integer movieId, RatingRequest ratingRequest, Authentication authentication) {
        movieService.rate(movieId, ratingRequest, authentication);
    }
}
