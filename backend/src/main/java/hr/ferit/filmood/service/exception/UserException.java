package hr.ferit.filmood.service.exception;

import hr.ferit.filmood.common.exception.AbstractServiceException;
import hr.ferit.filmood.service.exception.error.UserErrorKey;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

public class UserException extends AbstractServiceException {

    protected UserException(UserErrorKey errorKey, HttpStatus status) {
        super(errorKey, status);
    }

    protected UserException(UserErrorKey errorKey, HttpStatus status, String message) {
        super(errorKey, status, message);
    }

    protected UserException(UserErrorKey errorKey, HttpStatus status, String message, @Nullable Throwable cause) {
        super(errorKey, status, message, cause);
    }

    protected UserException(UserErrorKey errorKey, HttpStatus status, @Nullable Throwable cause) {
        super(errorKey, status, cause);
    }

    public static UserException notFound() {
        throw new UserException(UserErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public static UserException emailAlreadyExists() {
        throw new UserException(UserErrorKey.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }

    public static UserException usernameAlreadyExists() {
        throw new UserException(UserErrorKey.USERNAME_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }

    public static UserException blankOrNullPassword() {
        throw new UserException(UserErrorKey.BLANK_PASSWORD, HttpStatus.BAD_REQUEST);
    }
}
