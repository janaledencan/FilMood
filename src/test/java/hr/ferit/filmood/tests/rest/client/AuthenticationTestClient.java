package hr.ferit.filmood.tests.rest.client;

import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;
import hr.ferit.filmood.rest.api.authentication.request.CreateUpdateUserRequest;
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

    public static Response signup(CreateUpdateUserRequest request) {

        return RestAssured
                .given()
                .body(request)
                .post(String.format("%s/signup", AUTH_PATH));
    }

    public static Response update(CreateUpdateUserRequest request, String sessionId) {

        return RestAssured
                .given()
                .cookie("JSESSIONID", sessionId)
                .body(request)
                .put(String.format("%s/update", AUTH_PATH));
    }

    public static Response getCurrentUser(String sessionId) {

        return RestAssured
                .given()
                .cookie("JSESSIONID", sessionId)
                .get(String.format("%s/profile-info", AUTH_PATH));
    }

    public static void logout() {

        RestAssured.post(String.format("%s/logout", AUTH_PATH));
    }
}
