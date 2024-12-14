package hr.ferit.filmood.tests.rest.parameters;

import hr.ferit.filmood.tests.rest.factory.MovieFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static hr.ferit.filmood.tests.rest.constant.MovieConstants.BLANK_STRING;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.MOVIES_IN_LIBRARY_COUNT;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.MOVIES_IN_LIBRARY_COUNT_BY_RATING;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.MOVIES_TO_CHECK;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.RATED_MOVIES_IN_LIBRARY_COUNT;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.RATED_MOVIES_TO_CHECK;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.MOVIES_TO_CHECK_WITH_USER_RATING_SPECIFIED;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.MOVIE_VALID_QUERY_USER_RATING;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.MOVIE_VALID_QUERY_USER_RATING_NULL;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_GENRES;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_ID;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_POSTER_PATH;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_TITLE;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_VOTE_AVERAGE;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NEW_MOVIE_YEAR;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NULL_FLOAT;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.NULL_INTEGER;
import static hr.ferit.filmood.tests.rest.constant.MovieConstants.VALID_PAGE_SIZE;

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

    public static Stream<Arguments> libraryPageRequestAll() {

        List<Arguments> arguments = new ArrayList<>();
        int requiredNumberOfPages = (int) Math.ceil((double) MOVIES_IN_LIBRARY_COUNT /
                VALID_PAGE_SIZE);
        List<Integer> movieIds = Arrays.asList(MOVIES_TO_CHECK);

        for (int page = 0; page < requiredNumberOfPages; page++) {
            arguments.add(
                    Arguments.of(String.format("Page %d request", page), page,
                            VALID_PAGE_SIZE, MOVIE_VALID_QUERY_USER_RATING_NULL, movieIds.get(page))
            );
        }
        return arguments.stream();
    }

    public static Stream<Arguments> libraryPageRequestWithUserRating() {

        List<Arguments> arguments = new ArrayList<>();
        int requiredNumberOfPages = (int) Math.ceil((double) MOVIES_IN_LIBRARY_COUNT_BY_RATING /
                VALID_PAGE_SIZE);
        List<Integer> movieIds = Arrays.asList(MOVIES_TO_CHECK_WITH_USER_RATING_SPECIFIED);

        for (int page = 0; page < requiredNumberOfPages; page++) {
            arguments.add(
                    Arguments.of(String.format("Page %d request", page),
                            page, VALID_PAGE_SIZE, MOVIE_VALID_QUERY_USER_RATING, movieIds.get(page))
            );
        }
        return arguments.stream();
    }

    public static Stream<Arguments> libraryPageRequestRatedMovies() {

        List<Arguments> arguments = new ArrayList<>();
        int requiredNumberOfPages = (int) Math.ceil((double) RATED_MOVIES_IN_LIBRARY_COUNT /
                VALID_PAGE_SIZE);
        List<Integer> movieIds = Arrays.asList(RATED_MOVIES_TO_CHECK);

        for (int page = 0; page < requiredNumberOfPages; page++) {
            arguments.add(
                    Arguments.of(String.format("Page %d request", page),
                            page, VALID_PAGE_SIZE, MOVIE_VALID_QUERY_USER_RATING_NULL, movieIds.get(page))
            );
        }
        return arguments.stream();
    }

}
