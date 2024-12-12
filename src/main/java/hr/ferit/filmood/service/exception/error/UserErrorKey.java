package hr.ferit.filmood.service.exception.error;

import hr.ferit.filmood.common.exception.error.ErrorKey;

public enum UserErrorKey implements ErrorKey {
    NOT_FOUND,
    EMAIL_ALREADY_EXISTS,
    BLANK_PASSWORD,
    USERNAME_ALREADY_EXISTS;

    @Override
    public String getKey() {
        return String.format("user__%s", this.name().toLowerCase());
    }
}
