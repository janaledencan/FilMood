package hr.ferit.filmood.tests.rest.tests;

import hr.ferit.filmood.persistence.entity.MovieEntity;
import hr.ferit.filmood.persistence.repository.MovieRepository;
import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import hr.ferit.filmood.tests.BaseIT;
import hr.ferit.filmood.tests.rest.client.AuthenticationTestClient;
import hr.ferit.filmood.tests.rest.client.MovieTestClient;
import hr.ferit.filmood.tests.rest.factory.AuthenticationFactory;
import hr.ferit.filmood.tests.rest.factory.MovieFactory;
import io.restassured.response.Response;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_PASSWORD;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_USERNAME;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.EXISTING_MOVIE_GENRES;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.EXISTING_MOVIE_ID;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.EXISTING_MOVIE_POSTER_PATH;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.EXISTING_MOVIE_TITLE;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.EXISTING_MOVIE_VOTE_AVERAGE;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.EXISTING_MOVIE_YEAR;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_GENRES;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_ID;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_POSTER_PATH;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_TITLE;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_VOTE_AVERAGE;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_YEAR;

public class MovieApiIT extends BaseIT {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("Add movie to library - 200 Ok")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenValidRequest_whenAddMovieToLibrary_success200() {

        Response loginResponse = AuthenticationTestClient.authenticate(
                AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME,
                        EXISTING_USER_PASSWORD)
        );

        MovieTestClient
                .addToLibrary(MovieFactory.addMovieToLibraryRequest(
                        NEW_MOVIE_TITLE,
                        NEW_MOVIE_GENRES,
                        NEW_MOVIE_ID,
                        NEW_MOVIE_YEAR,
                        NEW_MOVIE_VOTE_AVERAGE,
                        NEW_MOVIE_POSTER_PATH),
                        loginResponse.sessionId()
                )
                .then()
                .statusCode(HttpStatus.OK.value());

        Optional<MovieEntity> movieOpt = movieRepository
                .findFirstByUserUsernameAndMovieId(EXISTING_USER_USERNAME, NEW_MOVIE_ID);

        if(movieOpt.isPresent()) {
            MovieEntity movie = movieOpt.get();
            Assertions.assertEquals(movie.getTitle(), NEW_MOVIE_TITLE);
            Assertions.assertEquals(movie.getGenres(), String.join(",", NEW_MOVIE_GENRES));
            Assertions.assertEquals(movie.getYear(), NEW_MOVIE_YEAR);
            Assertions.assertEquals(movie.getUserRating(), 0);
            Assertions.assertEquals(movie.getVoteAverage(), NEW_MOVIE_VOTE_AVERAGE);
            Assertions.assertEquals(movie.getPosterPath(), NEW_MOVIE_POSTER_PATH);
            Assertions.assertEquals(movie.getMovieId(), NEW_MOVIE_ID);
        } else {
            Assertions.fail();
        }

        AuthenticationTestClient.logout();
    }

    @Test
    @DisplayName("Add movie to library (movie already in library) - 400 Bad request")
    @FlywayTest(locationsForMigrate = {"migrations/users", "migrations/movies"})
    public void givenValidRequest_whenAddMovieToLibrary_alreadyInLibrary_failure400() {

        Response loginResponse = AuthenticationTestClient.authenticate(
                AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME,
                        EXISTING_USER_PASSWORD)
        );

        MovieTestClient
                .addToLibrary(MovieFactory.addMovieToLibraryRequest(
                                EXISTING_MOVIE_TITLE,
                                EXISTING_MOVIE_GENRES,
                                EXISTING_MOVIE_ID,
                                EXISTING_MOVIE_YEAR,
                                EXISTING_MOVIE_VOTE_AVERAGE,
                                EXISTING_MOVIE_POSTER_PATH),
                        loginResponse.sessionId()
                )
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        AuthenticationTestClient.logout();
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("hr.ferit.filmood.tests.rest.parameters.MovieParameters#badRequestAddMovieToLibrary")
    @DisplayName("Add movie to library - 400 Bad request")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenInvalidRequest_whenAddMovieToLibrary_failure400(AddMovieToLibraryRequest addMovieToLibraryRequest,
                                                                   String name) {

        Response loginResponse = AuthenticationTestClient.authenticate(
                AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME,
                        EXISTING_USER_PASSWORD)
        );

        MovieTestClient
                .addToLibrary(addMovieToLibraryRequest, loginResponse.sessionId())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        AuthenticationTestClient.logout();
    }

    @Test
    @DisplayName("Remove movie from library - 200 Ok")
    @FlywayTest(locationsForMigrate = {"migrations/users", "migrations/movies"})
    public void givenValidRequest_whenRemoveMovieFromLibrary_success200() {

        Response loginResponse = AuthenticationTestClient.authenticate(
                AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME,
                        EXISTING_USER_PASSWORD)
        );

        MovieTestClient
                .removeFromLibrary(EXISTING_MOVIE_ID, loginResponse.sessionId())
                .then()
                .statusCode(HttpStatus.OK.value());

        Optional<MovieEntity> movieOpt = movieRepository
                .findFirstByUserUsernameAndMovieId(EXISTING_USER_USERNAME, EXISTING_MOVIE_ID);

        if(movieOpt.isPresent()) {
            Assertions.fail();
        }

        AuthenticationTestClient.logout();
    }

    @Test
    @DisplayName("Remove movie from library (movie is not in library) - 400 Bad request")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenValidRequest_whenRemoveMovieFromLibrary_failure400() {

        Response loginResponse = AuthenticationTestClient.authenticate(
                AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME,
                        EXISTING_USER_PASSWORD)
        );

        MovieTestClient
                .removeFromLibrary(EXISTING_MOVIE_ID, loginResponse.sessionId())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        AuthenticationTestClient.logout();
    }
}
