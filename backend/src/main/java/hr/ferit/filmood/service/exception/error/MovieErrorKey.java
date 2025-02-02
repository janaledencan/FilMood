package hr.ferit.filmood.service.exception.error;

import hr.ferit.filmood.common.exception.error.ErrorKey;

public enum MovieErrorKey implements ErrorKey {
    API_ERROR,
    ALREADY_IN_LIBRARY,
    NOT_IN_LIBRARY;

    @Override
    public String getKey() {
        return String.format("movie__%s", this.name().toLowerCase());
    }
}
