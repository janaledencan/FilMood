package hr.ferit.filmood.tests.rest.parameters;

import hr.ferit.filmood.tests.rest.factory.MovieFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static hr.ferit.filmood.tests.rest.constant.MovieConstants.BLANK_STRING;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_GENRES;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_ID;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_POSTER_PATH;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_TITLE;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_VOTE_AVERAGE;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_YEAR;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NULL_FLOAT;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NULL_INTEGER;

public class MovieParameters {

    public static Stream<Arguments> badRequestAddMovieToLibrary() {

        return Stream.of(
        Arguments.of(MovieFactory.addMovieToLibraryRequest(
                                BLANK_STRING,
                                NEW_MOVIE_GENRES,
                                NEW_MOVIE_ID,
                                NEW_MOVIE_YEAR,
                                NEW_MOVIE_VOTE_AVERAGE,
                                NEW_MOVIE_POSTER_PATH),
                        "Blank title"),
                Arguments.of(MovieFactory.addMovieToLibraryRequest(
                                NEW_MOVIE_TITLE,
                                NEW_MOVIE_GENRES,
                                NULL_INTEGER,
                                NEW_MOVIE_YEAR,
                                NEW_MOVIE_VOTE_AVERAGE,
                                NEW_MOVIE_POSTER_PATH),
                        "Null movieId"),
                Arguments.of(MovieFactory.addMovieToLibraryRequest(
                                NEW_MOVIE_TITLE,
                                NEW_MOVIE_GENRES,
                                NEW_MOVIE_ID,
                                NULL_INTEGER,
                                NEW_MOVIE_VOTE_AVERAGE,
                                NEW_MOVIE_POSTER_PATH),
                        "Null year"),
                Arguments.of(MovieFactory.addMovieToLibraryRequest(
                                NEW_MOVIE_TITLE,
                                NEW_MOVIE_GENRES,
                                NEW_MOVIE_ID,
                                NEW_MOVIE_YEAR,
                                NULL_FLOAT,
                                NEW_MOVIE_POSTER_PATH),
                        "Null vote average")
        );
    }
}
