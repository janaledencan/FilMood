package hr.ferit.filmood.common.datetime;

import java.time.Clock;

public class UtcJavaTimeProvider extends AbstractJavaTimeProvider {

    public UtcJavaTimeProvider() {
        super(Clock.systemUTC());
    }

}
