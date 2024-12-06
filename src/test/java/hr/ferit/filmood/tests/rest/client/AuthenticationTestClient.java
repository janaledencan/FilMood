package hr.ferit.filmood.tests.rest.client;

import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static hr.ferit.filmood.common.CommonConstants.AUTH_PATH;

public class AuthenticationTestClient {

    public static Response authenticate(AuthRequest request) {

        return RestAssured
                .given()
                .body(request)
                .post(String.format("%s/login", AUTH_PATH));
    }
}
