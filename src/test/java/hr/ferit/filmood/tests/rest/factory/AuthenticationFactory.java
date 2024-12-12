package hr.ferit.filmood.tests.rest.factory;

import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;
import hr.ferit.filmood.rest.api.authentication.request.CreateUserRequest;

public class AuthenticationFactory {

    public static AuthRequest authRequest(String username, String password) {
        return new AuthRequest(username, password);
    }

    public static CreateUserRequest createUserRequest(String username,
                                                      String password,
                                                      String firstName,
                                                      String lastName,
                                                      String email,
                                                      Integer age,
                                                      String gender) {

        return new CreateUserRequest(username, password, firstName, lastName, email, age, gender);
    }
}
