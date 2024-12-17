package hr.ferit.filmood.common.exception.response;

import lombok.Getter;

import java.util.Objects;
import java.util.StringJoiner;

@Getter
public class FieldError {

    private final String name;
    private final String errorTemplate;
    private final String defaultMessage;

    public FieldError(String name, String errorTemplate, String defaultMessage) {
        this.name = name;
        this.errorTemplate = errorTemplate;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FieldError that = (FieldError) o;
        return getName().equals(that.getName()) &&
                getErrorTemplate().equals(that.getErrorTemplate()) &&
                getDefaultMessage().equals(that.getDefaultMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getErrorTemplate(), getDefaultMessage());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FieldError.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("errorTemplate='" + errorTemplate + "'")
                .add("message='" + defaultMessage + "'")
                .toString();
    }
}
