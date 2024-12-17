package hr.ferit.filmood.common.exception.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import hr.ferit.filmood.common.exception.error.ErrorKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@Getter
public class ErrorResponse {

    @NotNull
    private final Instant timestamp;
    @NotBlank
    private final String errorKey;
    @NotBlank
    private final String path;
    @NotBlank
    private final Set<FieldError> errors;
    @Nullable
    private String message;

    @Nullable
    private String stackTrace;

    public ErrorResponse(Instant timestamp, ErrorKey errorKey, String path) {
        this(timestamp, errorKey, path, null);
    }

    @JsonCreator
    public ErrorResponse(Instant timestamp, ErrorKey errorKey, String path, @Nullable String message) {
        this.timestamp = timestamp;
        this.errorKey = errorKey.getKey();
        this.message = StringUtils.defaultIfBlank(message, null);
        this.path = path.replace("uri=", "");
        this.errors = new HashSet<>();
    }

    public void addErrors(Set<FieldError> errors) {
        this.errors.addAll(errors);
    }

    public void addError(FieldError error) {
        this.errors.add(error);
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }

    @Nullable
    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(@Nullable String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorResponse that = (ErrorResponse) o;
        return timestamp.equals(that.timestamp) &&
                errorKey.equals(that.errorKey) &&
                path.equals(that.path) &&
                errors.equals(that.errors) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, errorKey, path, errors, message);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorResponse.class.getSimpleName() + "[", "]")
                .add("timestamp=" + timestamp)
                .add("errorKey='" + errorKey + "'")
                .add("path='" + path + "'")
                .add("errors=" + errors)
                .add("message='" + message + "'")
                .add("stacktrace='" + stackTrace + "'")
                .toString();
    }
}
