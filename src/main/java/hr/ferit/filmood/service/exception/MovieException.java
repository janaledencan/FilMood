package hr.ferit.filmood.service.exception;

import hr.ferit.filmood.common.exception.AbstractServiceException;
import hr.ferit.filmood.service.exception.error.MovieErrorKey;
import org.springframework.http.HttpStatus;

public class MovieException extends AbstractServiceException {

    protected MovieException(MovieErrorKey errorKey, HttpStatus status) {
        super(errorKey, status);
    }

    protected MovieException(MovieErrorKey errorKey, HttpStatus status, String message) {
        super(errorKey, status, message);
    }

    protected MovieException(MovieErrorKey errorKey, HttpStatus status, String message, Throwable cause) {
        super(errorKey, status, message, cause);
    }

    protected MovieException(MovieErrorKey errorKey, HttpStatus status, Throwable cause) {
        super(errorKey, status, cause);
    }

    public static MovieException apiException(MovieErrorKey errorKey, HttpStatus status, String message) {
        throw new MovieException(errorKey, status, message);
    }
}
