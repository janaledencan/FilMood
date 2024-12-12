package hr.ferit.filmood.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.ferit.filmood.common.rest.PageDTO;
import hr.ferit.filmood.configuration.properties.FilMoodProperties;
import hr.ferit.filmood.persistence.entity.GenreEntity;
import hr.ferit.filmood.persistence.entity.MovieEntity;
import hr.ferit.filmood.persistence.repository.GenreRepository;
import hr.ferit.filmood.persistence.repository.MovieRepository;
import hr.ferit.filmood.rest.api.movie.dto.MovieApiDTO;
import hr.ferit.filmood.rest.api.movie.dto.MovieDTO;
import hr.ferit.filmood.rest.api.movie.response.MovieApiPagedResponse;
import hr.ferit.filmood.rest.api.movie.response.MoviePagedResponse;
import hr.ferit.filmood.service.exception.MovieException;
import hr.ferit.filmood.service.exception.error.MovieErrorKey;
import hr.ferit.filmood.service.utils.UserUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static hr.ferit.filmood.common.CommonConstants.API_BASE_URL;
import static hr.ferit.filmood.common.CommonConstants.DEFAULT_API_PAGE_SIZE;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private final OkHttpClient okHttpClient;
    private final FilMoodProperties filMoodProperties;
    private final static String GET_MOVIE_LIST_URL = "/movie";
    private final ObjectMapper objectMapper;
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final UserUtils userUtils;

    public MovieServiceImpl(OkHttpClient okHttpClient, FilMoodProperties filMoodProperties, ObjectMapper objectMapper, GenreRepository genreRepository,
                            MovieRepository movieRepository,
                            UserUtils userUtils) {
        this.okHttpClient = okHttpClient;
        this.filMoodProperties = filMoodProperties;
        this.objectMapper = objectMapper;
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.userUtils = userUtils;
    }

    @Override
    public MoviePagedResponse getMovieList(String movieList, Integer number, Authentication authentication) {

        Request request = new Request.Builder()
                .url(String.format("%s%s/%s?page=%s", API_BASE_URL, GET_MOVIE_LIST_URL, movieList, number))
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

            if (!response.isSuccessful()) {
                throw MovieException.apiException(MovieErrorKey.API_ERROR, HttpStatus.valueOf(response.code()), response.message());
            }

            ResponseBody body = response.body();
            MovieApiPagedResponse moviesRetrieved = objectMapper.readValue(body.string(), MovieApiPagedResponse.class);
            pageDTO = new PageDTO(number,
                    (long) number * DEFAULT_API_PAGE_SIZE > moviesRetrieved.totalElements() ? (int) (moviesRetrieved.totalElements() % DEFAULT_API_PAGE_SIZE) : DEFAULT_API_PAGE_SIZE,
                    moviesRetrieved.totalPages(),
                    moviesRetrieved.totalElements()
            );
            if(moviesRetrieved.content() != null) {
                apiMovies = moviesRetrieved.content();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!apiMovies.isEmpty()) {
            List<GenreEntity> allGenres = genreRepository.findAll();
            List<Integer> library = movieRepository.findAllByUser(userUtils.getCurrentUser(authentication))
                    .stream()
                    .map(MovieEntity::getMovieId)
                    .toList();
            Dictionary<Integer, String> genresDict = new Hashtable<>();

            for(var genre: allGenres){
                genresDict.put(genre.getGenreId(), genre.getName());
            }

            for(var apiMovie : apiMovies) {
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
