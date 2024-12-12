package hr.ferit.filmood.rest.api.authentication.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static hr.ferit.filmood.common.CommonConstants.EMAIL_REGEX;
import static hr.ferit.filmood.common.CommonConstants.GENDER_REGEX;
import static hr.ferit.filmood.common.CommonConstants.PASSWORD_REGEX;

public record CreateUserRequest(@NotBlank String username,
                                @NotBlank @Pattern(regexp = PASSWORD_REGEX) String password,
                                @NotBlank String firstName,
                                @NotBlank String lastName,
                                @NotBlank @Pattern(regexp = EMAIL_REGEX) String email,
                                @NotNull @Min(0) @Max(120) Integer age,
                                @NotBlank @Pattern(regexp = GENDER_REGEX) String gender
                                ) {

    @JsonCreator
    public CreateUserRequest {

    }
}
