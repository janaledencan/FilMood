package hr.ferit.filmood.common.exception.resolver;

import hr.ferit.filmood.common.datetime.JavaTimeProvider;
import hr.ferit.filmood.common.datetime.UtcJavaTimeProvider;
import hr.ferit.filmood.common.exception.AbstractServiceException;
import hr.ferit.filmood.common.exception.ErrorUtils;
import hr.ferit.filmood.common.exception.error.GenericExceptionErrorKey;
import hr.ferit.filmood.common.exception.response.ErrorResponse;
import hr.ferit.filmood.common.exception.response.FieldError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

import java.beans.PropertyChangeEvent;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class AbstractExceptionResolver {

    public static final String VALIDATION_MESSAGE = "Validation errors";

    public static final String ISE_MESSAGE = "Unexpected Error Occurred";

    public static final String CLIENT_ERROR_MESSAGE = "Client error";

    public static final String AUTH_ERROR_MESSAGE = "Auth error";

    public JavaTimeProvider javaTimeProvider;

    public AbstractExceptionResolver() {
        this.javaTimeProvider = new UtcJavaTimeProvider();
    }

    public AbstractExceptionResolver setDateTimeProvider(JavaTimeProvider javaTimeProvider) {
        this.javaTimeProvider = javaTimeProvider;
        return this;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> fallbackHandleError(Exception e, ServletWebRequest request) {
        String message = e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(javaTimeProvider.instant(), new GenericExceptionErrorKey(e), request.getDescription(false), message);
        return toErrorResponseEntity(e, errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AbstractServiceException.class})
    public ResponseEntity<ErrorResponse> handleError(AbstractServiceException e, ServletWebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(javaTimeProvider.instant(), e.getErrorKey(), request.getDescription(false));
        e.getErrors().forEach((String key, String error) -> errorResponse.addError(new FieldError(key, error, error)));
        return toErrorResponseEntity(e, errorResponse, e.getStatus());
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleError(AuthenticationException e, ServletWebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(javaTimeProvider.instant(), new GenericExceptionErrorKey(e), request.getDescription(false));
        return toErrorResponseEntity(e, errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleError(AccessDeniedException e, ServletWebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(javaTimeProvider.instant(), new GenericExceptionErrorKey(e), request.getDescription(false));
        return toErrorResponseEntity(e, errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleError(ConstraintViolationException e, ServletWebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(javaTimeProvider.instant(), new GenericExceptionErrorKey(e), request.getDescription(false), VALIDATION_MESSAGE);

        Set<FieldError> fieldErrors = e.getConstraintViolations().stream()
                .map(this::toFieldError)
                .collect(Collectors.toSet());
        errorResponse.addErrors(fieldErrors);

        return toErrorResponseEntity(e, errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleError(IllegalArgumentException e, ServletWebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(javaTimeProvider.instant(), new GenericExceptionErrorKey(e), request.getDescription(false), VALIDATION_MESSAGE);
        return toErrorResponseEntity(e, errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleError(HttpMessageNotReadableException e, ServletWebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(javaTimeProvider.instant(), new GenericExceptionErrorKey(e), request.getDescription(false), e.getMessage());
        return toErrorResponseEntity(e, errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorResponse> handleError(HttpRequestMethodNotSupportedException e, ServletWebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(javaTimeProvider.instant(), new GenericExceptionErrorKey(e), request.getDescription(false), e.getMessage());
        return toErrorResponseEntity(e, errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleError(MethodArgumentNotValidException e, ServletWebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(javaTimeProvider.instant(), new GenericExceptionErrorKey(e), request.getDescription(false), VALIDATION_MESSAGE);

        e.getFieldErrors().stream()
                .map(this::convert)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(errorResponse::addError);

        if (errorResponse.getErrors().isEmpty()) {
            FieldError fieldError = new FieldError(e.getParameter().getParameter().getName(), "not_valid", e.getMessage());
            errorResponse.addError(fieldError);
        }

        return toErrorResponseEntity(e, errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> toErrorResponseEntity(Exception e, ErrorResponse errorResponse, HttpStatus status) {

        if (status.is4xxClientError()) {
            errorResponse.setMessage(CLIENT_ERROR_MESSAGE);
        } else {
            errorResponse.setMessage(ISE_MESSAGE);
        }

        if (status.is5xxServerError()) {
            log.error(e.getMessage(), e);
        }

        return ResponseEntity.status(status).body(errorResponse);
    }

    private FieldError toFieldError(ConstraintViolation<?> constraintViolation) {
        return new FieldError(
                constraintViolation.getPropertyPath().toString(),
                ErrorUtils.tryConvertMessageTemplate(constraintViolation.getMessageTemplate()),
                constraintViolation.getMessage()
        );
    }

    private Optional<FieldError> convert(org.springframework.validation.FieldError fieldError) {

        try {
            return Optional.of(toFieldError(fieldError.unwrap(ConstraintViolation.class)));
        }
        catch (Exception e) {
            // ignore
        }

        try {
            return Optional.of(toFieldError(fieldError.unwrap(PropertyChangeEvent.class)));
        }
        catch (Exception e) {
            // ignore
        }

        return Optional.empty();
    }

    private FieldError toFieldError(PropertyChangeEvent event) {
        if (event.getSource() instanceof ConstraintViolation<?> violation) {
            return toFieldError(violation);
        }

        return new FieldError(
                event.getPropertyName(),
                "not_allowed",
                String.format("New value of %s inside %s cannot be %s", event.getPropertyName(), event.getSource(), event.getNewValue())
        );
    }
}
