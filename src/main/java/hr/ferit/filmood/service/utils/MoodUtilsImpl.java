package hr.ferit.filmood.service.utils;

import org.springframework.stereotype.Component;

@Component
public class MoodUtilsImpl implements MoodUtils {

    private static final String MOOD_MOTIVATED = "motivated";
    private static final String MOOD_IN_LOVE = "in-love";
    private static final String MOOD_SAD = "sad";
    private static final String MOOD_BRAVE = "brave";
    private static final String MOOD_CURIOUS = "curious";
    private static final String MOOD_EXCITED = "excited";

    // mapping moods to genre ids
    private static final String MOTIVATED = "12|99";
    private static final String IN_LOVE = "18|10749";
    private static final String SAD = "10751|35|10402";
    private static final String BRAVE = "27|53|10752|37|80";
    private static final String CURIOUS = "36|14|99|9648";
    private static final String EXCITED = "878|12|28";

    @Override
    public String mapToGenres(String mood) {

        return switch(mood) {
            case MOOD_MOTIVATED -> MOTIVATED;
            case MOOD_BRAVE -> BRAVE;
            case MOOD_EXCITED -> EXCITED;
            case MOOD_SAD -> SAD;
            case MOOD_IN_LOVE -> IN_LOVE;
            case MOOD_CURIOUS -> CURIOUS;
            default -> throw new IllegalArgumentException("Unexpected value: " + mood);
        };
    }
}
