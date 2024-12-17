package hr.ferit.filmood.rest.api.movie.response;

import hr.ferit.filmood.common.rest.PageDTO;
import hr.ferit.filmood.common.rest.PagedResponse;
import hr.ferit.filmood.rest.api.movie.dto.LibraryMovieDTO;

import java.util.List;

public class LibraryPagedResponse extends PagedResponse<LibraryMovieDTO> {

    public LibraryPagedResponse(PageDTO page, List<LibraryMovieDTO> content) {
        super(page, content);
    }
}
