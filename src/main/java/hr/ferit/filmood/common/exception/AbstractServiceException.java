package hr.ferit.filmood.common.exception;

import hr.ferit.filmood.common.exception.error.ErrorKey;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class AbstractServiceException extends RuntimeException implements ServiceException {

    protected final ErrorKey errorKey;

    protected final HttpStatus status;

    protected final Map<String, String> errors;

    protected AbstractServiceException(ErrorKey errorKey, HttpStatus status) {
        this(errorKey, status, "");
    }

    protected AbstractServiceException(ErrorKey errorKey, HttpStatus status, String message) {
        this(errorKey, status, message, null);
    }

    protected AbstractServiceException(ErrorKey errorKey, HttpStatus status, String message, @Nullable Throwable cause) {
        super(message, cause);
        this.errorKey = errorKey;
        this.status = status;
        this.errors = new HashMap<>();
    }

    protected AbstractServiceException(ErrorKey errorKey, HttpStatus status, @Nullable Throwable cause) {
        this(errorKey, status, "", cause);
    }

    public AbstractServiceException addError(String key, String error) {
        this.errors.put(key, error);
        return this;
    }
}
