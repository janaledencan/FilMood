package hr.ferit.filmood.rest.impl.authentication;

import hr.ferit.filmood.rest.api.authentication.AuthenticationController;
import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;
import hr.ferit.filmood.rest.api.authentication.response.LogoutResponse;
import hr.ferit.filmood.rest.api.authentication.response.SessionExpiredResponse;
import hr.ferit.filmood.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationControllerImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void authenticate(AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response) {
        authenticationService.authenticate(authRequest, request, response);
    }

    @Override
    public SessionExpiredResponse sessionExpired() {
        return authenticationService.sessionExpired();
    }

    @Override
    public LogoutResponse logoutSuccess() {
        return authenticationService.logoutSuccess();
    }
}
