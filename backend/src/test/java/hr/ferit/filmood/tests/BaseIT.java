package hr.ferit.filmood.tests;

import hr.ferit.filmood.FilmoodApplication;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.annotation.FlywayTestExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;

@ActiveProfiles({"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Testcontainers
@Slf4j
@ContextConfiguration(classes = {FilmoodApplication.class})
@FlywayTest
@FlywayTestExtension
@ExtendWith(MockitoExtension.class)
public abstract class BaseIT {

    @LocalServerPort
    protected int port;

    protected RestAssuredConfig config;

    @Autowired
    protected Flyway flyway;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    @AfterAll
    static void afterAll() {
    }

    @BeforeEach
    void baseSetup(TestInfo testInfo) {
        // Set to error to be able to see which method was executed in build jobs.
        // Root level is set to error, hence the reason why this part is set to error as well.
        log.info("Running test for {}#{}", testInfo.getTestClass().get().getSimpleName(), testInfo.getTestMethod().get().getName());
        Thread.currentThread().setName(Thread.currentThread().getId() + "-" + this.getClass().getSimpleName());
    }

    @AfterEach
    void cleanup() {
        flyway.clean();
    }

    @PostConstruct
    public void setupRestAssured() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setPort(this.port)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();

        config = RestAssured.config
                .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset(StandardCharsets.UTF_8));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}