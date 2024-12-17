package hr.ferit.filmood.common.datetime;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;

public interface JavaTimeProvider {

    Clock clock();

    Instant instant();

    LocalTime localTime();

    LocalDate localDate();

    LocalDateTime localDateTime();

    OffsetTime offsetTime();

    OffsetDateTime offsetDateTime();
}
