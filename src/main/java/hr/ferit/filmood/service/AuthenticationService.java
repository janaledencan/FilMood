package hr.ferit.filmood.service;

import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;
import hr.ferit.filmood.rest.api.authentication.response.LogoutResponse;
import hr.ferit.filmood.rest.api.authentication.response.SessionExpiredResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    void authenticate(AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response);

    SessionExpiredResponse sessionExpired();

    LogoutResponse logoutSuccess();
}
