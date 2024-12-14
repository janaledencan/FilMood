package hr.ferit.filmood.tests.rest.constant;

import java.util.List;

public class MovieConstants {

    public static final String NEW_MOVIE_TITLE = "Movie";

    public static final List<String> NEW_MOVIE_GENRES = List.of("Action", "Comedy");

    public static final Integer NEW_MOVIE_ID = 107;

    public static final Integer NEW_MOVIE_YEAR = 2021;

    public static final Float NEW_MOVIE_VOTE_AVERAGE = 6.3f;

    public static final String NEW_MOVIE_POSTER_PATH = "/image.jpg";

    public static final String EXISTING_MOVIE_TITLE = "Something";

    public static final List<String> EXISTING_MOVIE_GENRES = List.of("Action", "Drama");

    public static final Integer EXISTING_MOVIE_ID = 5;

    public static final Integer EXISTING_MOVIE_YEAR = 2022;

    public static final Float EXISTING_MOVIE_VOTE_AVERAGE = 6.8f;

    public static final String EXISTING_MOVIE_POSTER_PATH = "/existing.jpg";

    public static final Integer NULL_INTEGER = null;

    public static final Float NULL_FLOAT = null;

    public static final String BLANK_STRING = " ";

    public static final Integer VALID_RATING = 4;

    public static final Integer INVALID_RATING = -1;

    public static final Integer NON_EXISTING_MOVIE_ID = 10;


    // constants for page request
    public static final Integer VALID_PAGE_SIZE = 2;

    public static final Long MOVIES_IN_LIBRARY_COUNT_BY_RATING = 3L;

    public static final Long RATED_MOVIES_IN_LIBRARY_COUNT = 4L;

    public static final Long MOVIES_IN_LIBRARY_COUNT = 5L;

    public static final Integer MOVIE_VALID_QUERY_USER_RATING = 5;

    public static final Integer MOVIE_VALID_QUERY_USER_RATING_NULL = null;

    public static Integer[] MOVIES_TO_CHECK = {1, 3, 5};

    public static Integer[] RATED_MOVIES_TO_CHECK = {1, 3};

    public static Integer[] MOVIES_TO_CHECK_WITH_USER_RATING_SPECIFIED = {1, 3};
}