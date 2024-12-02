package hr.ferit.filmood.common.exception.error;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GenericExceptionErrorKey implements ErrorKey {

    private static final String SPLIT_CASE_REGEX = "(?<=[a-z])(?=[A-Z])";

    private final Exception e;

    public GenericExceptionErrorKey(Exception e) {
        this.e = e;
    }

    @Override
    public String getKey() {
        String[] split = splitCaseFromClass(e.getClass());
        return Arrays.stream(split).filter(s -> !s.equalsIgnoreCase("exception"))
                .collect(Collectors.joining("_"))
                .toLowerCase();
    }

    private String[] splitCaseFromClass(Class<?> clazz) {
        return splitCase(clazz.getSimpleName());
    }

    private String[] splitCase(String name) {
        return name.split(SPLIT_CASE_REGEX);
    }
}
