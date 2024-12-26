package hr.ferit.filmood.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.ferit.filmood.common.rest.PageDTO;
import hr.ferit.filmood.common.rest.PagedResponse;
import hr.ferit.filmood.common.rest.movie.LibraryPageQuery;
import hr.ferit.filmood.configuration.properties.FilMoodProperties;
import hr.ferit.filmood.persistence.entity.GenreEntity;
import hr.ferit.filmood.persistence.entity.MovieEntity;
import hr.ferit.filmood.persistence.entity.UserEntity;
import hr.ferit.filmood.persistence.repository.GenreRepository;
import hr.ferit.filmood.persistence.repository.MovieRepository;
import hr.ferit.filmood.rest.api.genre.dto.GenreDTO;
import hr.ferit.filmood.rest.api.movie.dto.LibraryMovieDTO;
import hr.ferit.filmood.rest.api.movie.dto.MovieApiDTO;
import hr.ferit.filmood.rest.api.movie.dto.MovieApiDetailedDTO;
import hr.ferit.filmood.rest.api.movie.dto.MovieDTO;
import hr.ferit.filmood.rest.api.movie.dto.MovieDetailedDTO;
import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import hr.ferit.filmood.rest.api.movie.request.RatingRequest;
import hr.ferit.filmood.rest.api.movie.response.MovieApiPagedResponse;
import hr.ferit.filmood.rest.api.movie.response.MoviePagedResponse;
import hr.ferit.filmood.service.exception.MovieException;
import hr.ferit.filmood.service.exception.error.MovieErrorKey;
import hr.ferit.filmood.service.utils.MoodUtils;
import hr.ferit.filmood.service.utils.UserUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static hr.ferit.filmood.common.CommonConstants.API_BASE_URL;
import static hr.ferit.filmood.common.CommonConstants.DEFAULT_API_PAGE_SIZE;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private final static String GET_MOVIE_LIST_URL = "/movie";
    private final static String DISCOVER_MOVIE_LIST_URL = "/discover/movie";

    private final OkHttpClient okHttpClient;
    private final FilMoodProperties filMoodProperties;
    private final ObjectMapper objectMapper;
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final UserUtils userUtils;
    private final MoodUtils moodUtils;

    public MovieServiceImpl(OkHttpClient okHttpClient, FilMoodProperties filMoodProperties, ObjectMapper objectMapper, GenreRepository genreRepository,
                            MovieRepository movieRepository,
                            UserUtils userUtils, MoodUtils moodUtils) {
        this.okHttpClient = okHttpClient;
        this.filMoodProperties = filMoodProperties;
        this.objectMapper = objectMapper;
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.userUtils = userUtils;
        this.moodUtils = moodUtils;
    }

    @Override
    public MoviePagedResponse getMovieList(String movieList, Integer number, Authentication authentication) {

        Request request = new Request.Builder()
                .url(String.format("%s%s/%s?page=%s", API_BASE_URL, GET_MOVIE_LIST_URL, movieList.replace("-", "_"), number))
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", filMoodProperties.getApiBearerKey()))
                .build();

        return callApiAndReturnMoviePagedResponse(request, number, authentication);
    }

    @Override
    public void addToLibrary(AddMovieToLibraryRequest addMovieToLibraryRequest, Authentication authentication) {

        UserEntity currentUser = userUtils.getCurrentUser(authentication);

        if (movieRepository.findFirstByUserAndMovieId(currentUser, addMovieToLibraryRequest.movieId()).isPresent()) {
            throw MovieException.movieAlreadyInLibrary();
        }

        MovieEntity movie = new MovieEntity();
        movie.setUser(currentUser);
        movie.setUserRating(0);
        movie.setTitle(addMovieToLibraryRequest.title());
        movie.setMovieId(addMovieToLibraryRequest.movieId());
        movie.setYear(addMovieToLibraryRequest.releaseYear());
        movie.setPosterPath(addMovieToLibraryRequest.posterPath());
        movie.setVoteAverage(addMovieToLibraryRequest.voteAverage());
        movie.setGenres(String.join(",", addMovieToLibraryRequest.genres()));

        movieRepository.save(movie);
    }

    @Override
    public void removeFromLibrary(Integer movieId, Authentication authentication) {

        UserEntity currentUser = userUtils.getCurrentUser(authentication);

        MovieEntity movie = movieRepository.findFirstByUserAndMovieId(currentUser, movieId)
                .orElseThrow(MovieException::movieNotInLibrary);

        movieRepository.delete(movie);
    }

    @Override
    public MovieDetailedDTO getMovie(Integer movieId, Authentication authentication) {

        MovieApiDetailedDTO movieRetrieved;

        Request request = new Request.Builder()
                .url(String.format("%s%s/%s", API_BASE_URL, GET_MOVIE_LIST_URL, movieId))
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", filMoodProperties.getApiBearerKey()))
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {

            if (!response.isSuccessful() || response.body() == null) {
                throw MovieException.apiException(MovieErrorKey.API_ERROR, HttpStatus.valueOf(response.code()), response.message());
            }

            movieRetrieved = objectMapper.readValue(response.body().string(), MovieApiDetailedDTO.class);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Integer> library = movieRepository.findAllByUser(userUtils.getCurrentUser(authentication))
                .stream()
                .map(MovieEntity::getMovieId)
                .toList();

        return new MovieDetailedDTO(
                movieRetrieved.title(),
                movieRetrieved.genres().stream().map(GenreDTO::name).toList(),
                movieRetrieved.movieId(),
                Integer.valueOf(movieRetrieved.releaseDate().substring(0, 4)),
                movieRetrieved.voteAverage(),
                movieRetrieved.posterPath(),
                movieRetrieved.budget(),
                movieRetrieved.overview(),
                movieRetrieved.imdbId(),
                movieRetrieved.revenue(),
                movieRetrieved.runtime(),
                movieRetrieved.releaseStatus(),
                movieRetrieved.tagline(),
                movieRetrieved.voteCount(),
                library.contains(movieRetrieved.movieId())
        );
    }

    @Override
    public PagedResponse<LibraryMovieDTO> getLibrary(Boolean ratedOnly, Integer page, Integer size, String sort, String direction, Integer userRating, Authentication authentication) {

        LibraryPageQuery query = new LibraryPageQuery(page, size, sort, direction, userRating);
        Page<MovieEntity> moviesPage;
        query.setPage(query.getPage() - 1);

        if(ratedOnly != null && ratedOnly) {
            moviesPage = movieRepository.findAllByUserAndUserRatingBetween(userUtils.getCurrentUser(authentication), 1, 5, query.toPageRequest());
        }
        else if(query.getUserRating() != null) {
            moviesPage = movieRepository.findAllByUserAndUserRatingBetween(userUtils.getCurrentUser(authentication), query.getUserRating(), query.getUserRating(), query.toPageRequest());
        } else {
            moviesPage = movieRepository.findAllByUserAndUserRatingBetween(userUtils.getCurrentUser(authentication), 0, 5, query.toPageRequest());
        }

        List<LibraryMovieDTO> library = new ArrayList<>();

        for(var movie : moviesPage.getContent()) {
            library.add(new LibraryMovieDTO(
                    movie.getTitle(),
                    Arrays.asList(movie.getGenres().split(",")),
                    movie.getMovieId(),
                    movie.getYear(),
                    movie.getVoteAverage(),
                    movie.getPosterPath(),
                    movie.getUserRating()
            ));
        }

        return new PagedResponse<>(PageDTO.from(moviesPage), library);
    }

    @Override
    public void rate(Integer movieId, RatingRequest ratingRequest, Authentication authentication) {

        UserEntity currentUser = userUtils.getCurrentUser(authentication);

        MovieEntity movie = movieRepository.findFirstByUserAndMovieId(currentUser, movieId)
                .orElseThrow(MovieException::movieNotInLibrary);

        movie.setUserRating(ratingRequest.userRating());

        movieRepository.save(movie);
    }

    @Override
    public MoviePagedResponse getByMood(String mood, Integer number, Authentication authentication) {

        Request request = new Request.Builder()
                .url(String.format("%s%s?page=%s&with_genres=%s", API_BASE_URL, DISCOVER_MOVIE_LIST_URL, number, moodUtils.mapToGenres(mood)))
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", filMoodProperties.getApiBearerKey()))
                .build();

        return callApiAndReturnMoviePagedResponse(request, number, authentication);
    }

    private MoviePagedResponse callApiAndReturnMoviePagedResponse(Request request, Integer number, Authentication authentication) {

        List<MovieApiDTO> apiMovies = new ArrayList<>();
        List<MovieDTO> movies = new ArrayList<>();
        PageDTO pageDTO;

        try (Response response = okHttpClient.newCall(request).execute()) {

            if (!response.isSuccessful() || response.body() == null) {
                throw MovieException.apiException(MovieErrorKey.API_ERROR, HttpStatus.valueOf(response.code()), response.message());
            }

            MovieApiPagedResponse moviesRetrieved = objectMapper.readValue(response.body().string(), MovieApiPagedResponse.class);
            pageDTO = new PageDTO(number,
                    (long) number * DEFAULT_API_PAGE_SIZE > moviesRetrieved.totalElements() ? (int) (moviesRetrieved.totalElements() % DEFAULT_API_PAGE_SIZE) :
                            DEFAULT_API_PAGE_SIZE,
                    moviesRetrieved.totalPages(),
                    moviesRetrieved.totalElements()
            );
            if (moviesRetrieved.content() != null) {
                apiMovies = moviesRetrieved.content();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!apiMovies.isEmpty()) {
            List<GenreEntity> allGenres = genreRepository.findAll();
            List<Integer> library = movieRepository.findAllByUser(userUtils.getCurrentUser(authentication))
                    .stream()
                    .map(MovieEntity::getMovieId)
                    .toList();
            Dictionary<Integer, String> genresDict = new Hashtable<>();

            for (var genre : allGenres) {
                genresDict.put(genre.getGenreId(), genre.getName());
            }

            for (var apiMovie : apiMovies) {
                List<String> genres = apiMovie.genres().stream()
                        .map(genresDict::get)
                        .toList();
                movies.add(new MovieDTO(
                        apiMovie.title(),
                        genres,
                        apiMovie.movieId(),
                        Integer.valueOf(apiMovie.releaseDate().substring(0, 4)),
                        apiMovie.voteAverage(),
                        apiMovie.posterPath(),
                        library.contains(apiMovie.movieId())
                ));
            }
        }

        return new MoviePagedResponse(pageDTO, movies);
    }
}
