package hr.ferit.filmood.common.datetime;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;

public class AbstractJavaTimeProvider implements JavaTimeProvider {

    protected final Clock clock;

    protected AbstractJavaTimeProvider(final Clock clock) {
        this.clock = clock;
    }

    public Clock clock() {
        return clock;
    }

    public Instant instant() {
        return Instant.now(clock);
    }

    public LocalTime localTime() {
        return LocalTime.now(clock);
    }

    public LocalDate localDate() {
        return LocalDate.now(clock);
    }

    public LocalDateTime localDateTime() {
        return LocalDateTime.now(clock);
    }

    public OffsetTime offsetTime() {
        return OffsetTime.now(clock);
    }

    public OffsetDateTime offsetDateTime() {
        return OffsetDateTime.now(clock);
    }
}
