package hr.ferit.filmood.tests.rest.factory;

import hr.ferit.filmood.rest.api.movie.request.AddMovieToLibraryRequest;

import java.util.List;

public class MovieFactory {

    public static AddMovieToLibraryRequest addMovieToLibraryRequest(String title,
                                                                    List<String> genres,
                                                                    Integer movieId,
                                                                    Integer releaseYear,
                                                                    Float voteAverage,
                                                                    String posterPath) {

        return new AddMovieToLibraryRequest(title, genres, movieId, releaseYear, voteAverage, posterPath);
    }
}
