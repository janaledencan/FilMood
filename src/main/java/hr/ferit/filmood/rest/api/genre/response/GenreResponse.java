package hr.ferit.filmood.rest.api.genre.response;

import hr.ferit.filmood.rest.api.genre.dto.GenreDTO;

import java.util.List;

public record GenreResponse(List<GenreDTO> genres){
}
