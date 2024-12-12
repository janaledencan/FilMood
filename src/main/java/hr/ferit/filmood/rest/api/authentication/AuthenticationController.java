package hr.ferit.filmood.rest.api.authentication;

import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;
import hr.ferit.filmood.rest.api.authentication.request.CreateUpdateUserRequest;
import hr.ferit.filmood.rest.api.authentication.response.LogoutResponse;
import hr.ferit.filmood.rest.api.authentication.response.SessionExpiredResponse;
import hr.ferit.filmood.rest.api.authentication.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static hr.ferit.filmood.common.CommonConstants.AUTH_PATH;

@RequestMapping(AUTH_PATH)
@Validated
public interface AuthenticationController {

    @PostMapping("/login")
    void authenticate(@RequestBody @NotNull AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response);

    @PostMapping("/signup")
    void signup(@RequestBody @NotNull @Valid CreateUpdateUserRequest createUpdateUserRequest);

    @GetMapping("/profile-info")
    UserResponse getCurrentUser(Authentication authentication);

    @PostMapping("/update")
    void update(@RequestBody @NotNull @Valid CreateUpdateUserRequest createUpdateUserRequest, Authentication authentication);

    @RequestMapping("/session/expired")
    SessionExpiredResponse sessionExpired();

    @RequestMapping("/logout/success")
    LogoutResponse logoutSuccess();
}
