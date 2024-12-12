package hr.ferit.filmood.tests.rest.tests;

import hr.ferit.filmood.persistence.repository.UserRepository;
import hr.ferit.filmood.rest.api.authentication.request.CreateUserRequest;
import hr.ferit.filmood.tests.BaseIT;
import hr.ferit.filmood.tests.rest.client.AuthenticationTestClient;
import hr.ferit.filmood.tests.rest.factory.AuthenticationFactory;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_PASSWORD;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_USERNAME;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_AGE;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_EMAIL;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_FIRST_NAME;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_GENDER;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_LAST_NAME;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_PASSWORD;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_USERNAME;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.WRONG_PASSWORD;

public class AuthenticationApiIT extends BaseIT {

    @Autowired
    private UserRepository userRepository;

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

    @Test
    @DisplayName("User signs up - 200 Ok")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenValidRequest_whenSignUp_success200() {

        AuthenticationTestClient
                .signup(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER)
                )
                .then()
                .statusCode(HttpStatus.OK.value());

        Assertions.assertEquals(2, userRepository.findAll().size());
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("hr.ferit.filmood.tests.rest.parameters.UserParameters#badRequestForCreateUser")
    @DisplayName("User signs up - 400 Bad request")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenInvalidRequest_whenSignUp_failure400(CreateUserRequest createUserRequest, String name) {

        AuthenticationTestClient
                .signup(createUserRequest)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
