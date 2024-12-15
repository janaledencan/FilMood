package hr.ferit.filmood.rest.api.authentication;

import hr.ferit.filmood.common.exception.response.ErrorResponse;
import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;
import hr.ferit.filmood.rest.api.authentication.request.CreateUpdateUserRequest;
import hr.ferit.filmood.rest.api.authentication.response.LogoutResponse;
import hr.ferit.filmood.rest.api.authentication.response.SessionExpiredResponse;
import hr.ferit.filmood.rest.api.authentication.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static hr.ferit.filmood.common.CommonConstants.AUTH_PATH;

@RequestMapping(AUTH_PATH)
@Validated
public interface AuthenticationController {

    @Operation(
            summary = "Endpoint for login. To logout, instead of login, type logout.",
            description = "Endpoint for login. To logout, instead of login, type logout.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully logged in."
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    )
            }
    )
    @PostMapping("/login")
    void authenticate(@RequestBody @NotNull AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response);

    @Operation(
            summary = "Endpoint for signup. To logout, instead of signup, type logout.",
            description = "Endpoint for signup. To logout, instead of signup, type logout.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully signed up."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    )
            }
    )
    @PostMapping("/signup")
    void signup(@Parameter(description = "Username and email must be unique. Password and email have regex which they must respect. " +
            "You can find those regex in CreateUpdateRequest schema below under password and email parameters. If you don't understand them, ChatGPT does.")
                @RequestBody @NotNull @Valid CreateUpdateUserRequest createUpdateUserRequest);

    @Operation(
            summary = "Fetches User's profile information.",
            description = "Fetches User's profile information.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Information successfully fetched."
                    )
            }
    )
    @GetMapping("/profile-info")
    UserResponse getCurrentUser(Authentication authentication);

    @Operation(
            summary = "Endpoint for updating user information. To logout, instead of update, type logout.",
            description = "Endpoint for updating user information. To logout, instead of update, type logout.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User information successfully updated."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
                    )
            }
    )
    @PutMapping("/update")
    void update(@Parameter(description = "Username and email must be unique. Password and email have regex which they must respect. " +
            "You can find those regex in CreateUpdateRequest schema below under password and email parameters. If you don't understand them, ChatGPT does. " +
            "Here, password is not mandatory if User didn't specify new password to update the existing one. In that case, password stays the same as before.")
            @RequestBody @NotNull @Valid CreateUpdateUserRequest createUpdateUserRequest, Authentication authentication);

    @RequestMapping("/session/expired")
    SessionExpiredResponse sessionExpired();

    @RequestMapping("/logout/success")
    LogoutResponse logoutSuccess();
}
