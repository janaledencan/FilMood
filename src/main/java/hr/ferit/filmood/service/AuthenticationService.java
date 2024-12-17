package hr.ferit.filmood.service;

import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;
import hr.ferit.filmood.rest.api.authentication.request.CreateUpdateUserRequest;
import hr.ferit.filmood.rest.api.authentication.response.LogoutResponse;
import hr.ferit.filmood.rest.api.authentication.response.SessionExpiredResponse;
import hr.ferit.filmood.rest.api.authentication.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    void authenticate(AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response);

    void signup(CreateUpdateUserRequest createUpdateUserRequest);

    UserResponse getCurrentUser(Authentication authentication);

    void update(CreateUpdateUserRequest createUpdateUserRequest, Authentication authentication);

    SessionExpiredResponse sessionExpired();

    LogoutResponse logoutSuccess();
}
