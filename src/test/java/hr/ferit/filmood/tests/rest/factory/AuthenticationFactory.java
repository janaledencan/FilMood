package hr.ferit.filmood.tests.rest.factory;

import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;

public class AuthenticationFactory {

    public static AuthRequest authRequest(String username, String password) {
        return new AuthRequest(username, password);
    }
}
