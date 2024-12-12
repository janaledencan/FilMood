package hr.ferit.filmood.common;

public class CommonConstants {

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    // at least 6 characters, at least 1 uppercase and lowercase letter, at least 1 number
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";

    public static final String GENDER_REGEX = "^(Male|Female|Other)$";

    public static final String BASE_PATH = "/api/v1";

    public static final String AUTH_PATH = BASE_PATH + "/auth";
}
