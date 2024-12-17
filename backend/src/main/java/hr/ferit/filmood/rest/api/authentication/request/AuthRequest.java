package hr.ferit.filmood.rest.api.authentication.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public record AuthRequest(String username, String password) {

    @JsonCreator
    public AuthRequest {

    }
}
