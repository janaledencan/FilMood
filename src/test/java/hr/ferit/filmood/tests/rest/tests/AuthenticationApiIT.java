package hr.ferit.filmood.tests.rest.tests;

import hr.ferit.filmood.tests.BaseIT;
import hr.ferit.filmood.tests.rest.client.AuthenticationTestClient;
import hr.ferit.filmood.tests.rest.factory.AuthenticationFactory;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_PASSWORD;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_USERNAME;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.WRONG_PASSWORD;

public class AuthenticationApiIT extends BaseIT {

    @Test
    @DisplayName("Existing user logs in - 200 Ok")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenValidRequest_whenLogIn_success200() {

        AuthenticationTestClient
                .authenticate(AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME,
                        EXISTING_USER_PASSWORD)
                )
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("User provides wrong credentials - 401 Unauthorized")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenInvalidRequest_whenLogIn_failure401() {

        AuthenticationTestClient
                .authenticate(AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME,
                        WRONG_PASSWORD)
                )
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
