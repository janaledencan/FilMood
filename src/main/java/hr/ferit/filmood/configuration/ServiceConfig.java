package hr.ferit.filmood.configuration;

import hr.ferit.filmood.common.datetime.JavaTimeProvider;
import hr.ferit.filmood.common.datetime.UtcJavaTimeProvider;
import hr.ferit.filmood.configuration.properties.FilMoodProperties;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableJpaAuditing
@EntityScan("hr.ferit.filmood.persistence.entity")
@EnableJpaRepositories("hr.ferit.filmood.persistence.repository")
@EnableMethodSecurity
@EnableConfigurationProperties({FilMoodProperties.class})
public class ServiceConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaTimeProvider javaTimeProvider() {
        return new UtcJavaTimeProvider();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
