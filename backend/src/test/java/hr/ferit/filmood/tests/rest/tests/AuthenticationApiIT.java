package hr.ferit.filmood.tests.rest.tests;

import hr.ferit.filmood.persistence.entity.UserEntity;
import hr.ferit.filmood.persistence.repository.UserRepository;
import hr.ferit.filmood.rest.api.authentication.request.CreateUpdateUserRequest;
import hr.ferit.filmood.rest.api.authentication.response.UserResponse;
import hr.ferit.filmood.tests.BaseIT;
import hr.ferit.filmood.tests.rest.client.AuthenticationTestClient;
import hr.ferit.filmood.tests.rest.factory.AuthenticationFactory;
import io.restassured.response.Response;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_PASSWORD_MARK;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_USERNAME_MARK;
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
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("Existing user logs in - 200 Ok")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenValidRequest_whenLogIn_success200() {

        AuthenticationTestClient
                .authenticate(AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME_MARK,
                        EXISTING_USER_PASSWORD_MARK)
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
                        EXISTING_USER_USERNAME_MARK,
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
                .signup(AuthenticationFactory.createUpdateUserRequest(
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

        Assertions.assertEquals(3, userRepository.findAll().size());
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("hr.ferit.filmood.tests.rest.parameters.UserParameters#badRequestForCreateUser")
    @DisplayName("User signs up - 400 Bad request")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenInvalidRequest_whenSignUp_failure400(CreateUpdateUserRequest createUpdateUserRequest, String name) {

        AuthenticationTestClient
                .signup(createUpdateUserRequest)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("User updates profile - 200 Ok")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenValidRequest_whenUpdate_success200() {

        Response loginResponse = AuthenticationTestClient.authenticate(
                AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME_MARK,
                        EXISTING_USER_PASSWORD_MARK)
        );

        AuthenticationTestClient
                .update(AuthenticationFactory.createUpdateUserRequest(
                                NEW_USER_USERNAME,
                                NEW_USER_PASSWORD,
                                NEW_USER_FIRST_NAME,
                                NEW_USER_LAST_NAME,
                                NEW_USER_EMAIL,
                                NEW_USER_AGE,
                                NEW_USER_GENDER),
                        loginResponse.sessionId()
                )
                .then()
                .statusCode(HttpStatus.OK.value());

        Assertions.assertEquals(2, userRepository.findAll().size());

        Optional<UserEntity> updatedUserOpt = userRepository.findByUsername(NEW_USER_USERNAME);

        if(updatedUserOpt.isPresent()) {
            UserEntity updatedUser = updatedUserOpt.get();
            Assertions.assertEquals(updatedUser.getUsername(), NEW_USER_USERNAME);
            Assertions.assertTrue(bCryptPasswordEncoder.matches(NEW_USER_PASSWORD, updatedUser.getPassword()));
            Assertions.assertEquals(updatedUser.getFirstName(), NEW_USER_FIRST_NAME);
            Assertions.assertEquals(updatedUser.getLastName(), NEW_USER_LAST_NAME);
            Assertions.assertEquals(updatedUser.getEmail(), NEW_USER_EMAIL);
            Assertions.assertEquals(updatedUser.getAge(), NEW_USER_AGE);
            Assertions.assertEquals(updatedUser.getGender(), NEW_USER_GENDER);
        } else {
            Assertions.fail();
        }

        AuthenticationTestClient.logout();
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("hr.ferit.filmood.tests.rest.parameters.UserParameters#badRequestForUpdateUser")
    @DisplayName("User updates profile - 400 Bad request")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenInvalidRequest_whenUpdate_failure400(CreateUpdateUserRequest createUpdateUserRequest, String name) {

        Response loginResponse = AuthenticationTestClient.authenticate(
                AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME_MARK,
                        EXISTING_USER_PASSWORD_MARK)
        );

        AuthenticationTestClient
                .update(createUpdateUserRequest, loginResponse.sessionId())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        AuthenticationTestClient.logout();
    }

    @Test
    @DisplayName("User gets profile info - 200 Ok")
    @FlywayTest(locationsForMigrate = {"migrations/users"})
    public void givenValidRequest_whenGetCurrentUser_success200() {

        Response loginResponse = AuthenticationTestClient.authenticate(
                AuthenticationFactory.authRequest(
                        EXISTING_USER_USERNAME_MARK,
                        EXISTING_USER_PASSWORD_MARK)
        );

        UserResponse response = AuthenticationTestClient
                .getCurrentUser(loginResponse.sessionId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(UserResponse.class);

        Optional<UserEntity> userOpt = userRepository.findByUsername(EXISTING_USER_USERNAME_MARK);

        if(userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            Assertions.assertEquals(user.getUsername(), response.username());
            Assertions.assertEquals(user.getFirstName(), response.firstName());
            Assertions.assertEquals(user.getLastName(), response.lastName());
            Assertions.assertEquals(user.getEmail(), response.email());
            Assertions.assertEquals(user.getAge(), response.age());
            Assertions.assertEquals(user.getGender(), response.gender());
        } else {
            Assertions.fail();
        }

        AuthenticationTestClient.logout();
    }
}
