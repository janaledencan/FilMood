package hr.ferit.filmood.rest.api.authentication.response;

public record UserResponse(String username, String firstName, String lastName,
                           String email, Integer age, String gender) {
}
