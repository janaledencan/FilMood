package hr.ferit.filmood.tests.rest.parameters;

import hr.ferit.filmood.tests.rest.factory.AuthenticationFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.ABOVE_MAXIMUM_AGE;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.BELOW_MINIMUM_AGE;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.BLANK_STRING;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_EMAIL_HUGH;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_EMAIL_MARK;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_USERNAME_HUGH;
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.EXISTING_USER_USERNAME_MARK;
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
import static hr.ferit.filmood.tests.rest.constant.AuthenticationConstants.NULL_STRING;

public class UserParameters {

    public static Stream<Arguments> badRequestForCreateUser() {

        return Stream.of(
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        BLANK_STRING,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank first name"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        BLANK_STRING,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank last name"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        BLANK_STRING,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank username"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        BLANK_STRING,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank password"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NULL_STRING,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Null password"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        BLANK_STRING,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank email"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NULL_AGE,
                        NEW_USER_GENDER
                ), "Null age"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        BLANK_STRING
                ), "Blank gender"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        BELOW_MINIMUM_AGE,
                        NEW_USER_GENDER
                ), "Below minimum age"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        ABOVE_MAXIMUM_AGE,
                        NEW_USER_GENDER
                ), "Above maximum age"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        INVALID_GENDER
                ), "Invalid gender"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        INVALID_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Invalid password"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        INVALID_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Invalid email"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        EXISTING_USER_EMAIL_MARK,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Already existent email"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        EXISTING_USER_USERNAME_MARK,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Already existent username")
        );
    }

    public static Stream<Arguments> badRequestForUpdateUser() {

        return Stream.of(
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        BLANK_STRING,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank first name"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        BLANK_STRING,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank last name"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        BLANK_STRING,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank username"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        BLANK_STRING,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Blank email"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NULL_AGE,
                        NEW_USER_GENDER
                ), "Null age"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        BLANK_STRING
                ), "Blank gender"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        BELOW_MINIMUM_AGE,
                        NEW_USER_GENDER
                ), "Below minimum age"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        ABOVE_MAXIMUM_AGE,
                        NEW_USER_GENDER
                ), "Above maximum age"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        INVALID_GENDER
                ), "Invalid gender"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        INVALID_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Invalid password"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        INVALID_EMAIL,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Invalid email"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        NEW_USER_USERNAME,
                        NEW_USER_PASSWORD,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        EXISTING_USER_EMAIL_HUGH,
                        NEW_USER_AGE,
                        NEW_USER_GENDER
                ), "Already existent email"),
                Arguments.of(AuthenticationFactory.createUpdateUserRequest(
                        EXISTING_USER_USERNAME_HUGH,
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
