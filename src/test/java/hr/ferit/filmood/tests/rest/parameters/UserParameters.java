package hr.ferit.filmood.tests.rest.parameters;

import hr.ferit.filmood.tests.rest.factory.AuthenticationFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.ABOVE_MAXIMUM_AGE;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.BELOW_MINIMUM_AGE;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.BLANK_STRING;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_EMAIL;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_USERNAME;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.INVALID_EMAIL;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.INVALID_GENDER;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.INVALID_PASSWORD;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_AGE;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_EMAIL;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_FIRST_NAME;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_GENDER;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_LAST_NAME;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_PASSWORD;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NEW_USER_USERNAME;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NULL_AGE;

public class UserParameters {

    public static Stream<Arguments> badRequestForCreateUser() {

        return Stream.of(
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        BLANK_STRING,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank first name"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        BLANK_STRING,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank last name"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        BLANK_STRING,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank username"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        BLANK_STRING,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank password"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        BLANK_STRING,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank email"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NULL_AGE,
                        NEW_USER_GENDER
                ), "Null age"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        BLANK_STRING
                ), "Blank gender"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        BELOW_MINIMUM_AGE,
                        NEW_USER_GENDER
                ), "Below minimum age"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        ABOVE_MAXIMUM_AGE,
                        NEW_USER_GENDER
                ), "Above maximum age"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        INVALID_GENDER
                ), "Invalid gender"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        INVALID_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Invalid password"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        INVALID_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Invalid email"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        EXISTING_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Already existent email"),
                Arguments.of(AuthenticationFactory.createUserRequest(
                        EXISTING_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Already existent username")
        );
    }

}
