package hr.ferit.filmood.configuration.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "filmood")
public class FilMoodProperties {
}
