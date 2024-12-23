package hr.ferit.filmood.tests.rest.client;

import hr.ferit.filmood.common.rest.movie.LibraryPageQuery;
import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;
import hr.ferit.filmood.rest.api.movie.request.RatingRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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

    public static Response getLibrary(Boolean ratedOnly, LibraryPageQuery query, String sessionId) {

        RequestSpecification specification = RestAssured
                .given()
                .body(query)
                .cookie("JSESSIONID", sessionId)
                .param("rated-only", ratedOnly);

        if (query.getPage() != null) {
            specification.param("page", query.getPage());
        }

        if (query.getSize() != null) {
            specification.param("size", query.getSize());
        }

        if (query.getSort() != null) {
            specification.param("sort", query.getSort());
        }

        if (query.getDirection() != null) {
            specification.param("direction", query.getDirection());
        }

        if (query.getUserRating() != null) {
            specification.param("user-rating", query.getUserRating());
        }

        return specification.get(String.format("%s/library", MOVIE_PATH));
    }

    public static Response rate(Integer movieId, RatingRequest ratingRequest, String sessionId) {

        return RestAssured
                .given()
                .body(ratingRequest)
                .cookie("JSESSIONID", sessionId)
                .post(String.format("%s/library/%s", MOVIE_PATH, movieId));
    }
}
