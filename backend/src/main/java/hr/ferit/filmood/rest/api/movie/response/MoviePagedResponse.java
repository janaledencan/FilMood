package hr.ferit.filmood.rest.api.movie.response;

import hr.ferit.filmood.common.rest.PageDTO;
import hr.ferit.filmood.common.rest.PagedResponse;
import hr.ferit.filmood.rest.api.movie.dto.MovieDTO;

import java.util.List;

public class MoviePagedResponse extends PagedResponse<MovieDTO> {

    public MoviePagedResponse(PageDTO page, List<MovieDTO> content) {
        super(page, content);
    }
}
