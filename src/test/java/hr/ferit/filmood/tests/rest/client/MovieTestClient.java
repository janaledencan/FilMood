package hr.ferit.filmood.tests.rest.client;

import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static hr.ferit.filmood.common.CommonConstants.MOVIE_PATH;

public class MovieTestClient {

    public static Response addToLibrary(AddMovieToLibraryRequest request, String sessionId) {

        return RestAssured
                .given()
                .cookie("JSESSIONID", sessionId)
                .body(request)
                .post(String.format("%s/library", MOVIE_PATH));
    }

    public static Response removeFromLibrary(Integer movieId, String sessionId) {

        return RestAssured
                .given()
                .cookie("JSESSIONID", sessionId)
                .delete(String.format("%s/library/%s", MOVIE_PATH, movieId));
    }
}
