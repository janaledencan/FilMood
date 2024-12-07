package hr.ferit.filmood.service.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.ferit.filmood.configuration.properties.FilMoodProperties;
import hr.ferit.filmood.persistence.entity.GenreEntity;
import hr.ferit.filmood.persistence.repository.GenreRepository;
import hr.ferit.filmood.rest.api.genre.response.GenreResponse;
import hr.ferit.filmood.service.mapper.GenreMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
public class OnStartUp implements ApplicationListener<ContextRefreshedEvent> {

    private final GenreRepository genreRepository;
    private final FilMoodProperties filMoodProperties;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final GenreMapper genreMapper;

    public OnStartUp(GenreRepository genreRepository, OkHttpClient okHttpClient,
                     FilMoodProperties filMoodProperties, ObjectMapper objectMapper,
                     GenreMapper genreMapper) {

        this.genreRepository = genreRepository;
        this.okHttpClient = okHttpClient;
        this.filMoodProperties = filMoodProperties;
        this.objectMapper = objectMapper;
        this.genreMapper = genreMapper;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        List<GenreEntity> genresInDb = genreRepository.findAll();

        if(genresInDb.isEmpty()) {
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/genre/movie/list?language=en")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", String.format("Bearer %s", filMoodProperties.getApiBearerKey()))
                    .build();

            try {
                ResponseBody response = okHttpClient.newCall(request).execute().body();
                GenreResponse genresRetrieved = objectMapper.readValue(response.string(), GenreResponse.class);
                genreRepository.saveAll(genreMapper.mapEntity(genresRetrieved.genres()));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
