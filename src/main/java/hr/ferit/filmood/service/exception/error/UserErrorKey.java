package hr.ferit.filmood.service.exception.error;

import hr.ferit.filmood.common.exception.error.ErrorKey;

public enum UserErrorKey implements ErrorKey {
    NOT_FOUND;

    @Override
    public String getKey() {
        return String.format("user__%s", this.name().toLowerCase());
    }
}
