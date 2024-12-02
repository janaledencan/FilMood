package hr.ferit.filmood.common.exception;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ErrorUtils {

    public static final String SPLIT_CASE_REGEX = "(?<=[a-z])(?=[A-Z])";

    private ErrorUtils() {
    }

    public static String tryConvertMessageTemplate(String errorTemplate) {
        return Arrays.stream(errorTemplate.split("\\."))
                .filter((String string) -> Character.isUpperCase(string.charAt(0)))
                .findFirst()
                .map(ErrorUtils::toErrorKey)
                .orElse(errorTemplate);
    }

    private static String toErrorKey(String message) {
        return ErrorUtils.splitCase(message)
                .map(String::toLowerCase)
                .collect(Collectors.joining("_"));
    }

    public static Stream<String> splitCase(String name) {
        return Arrays.stream(name.split(SPLIT_CASE_REGEX));
    }
}
