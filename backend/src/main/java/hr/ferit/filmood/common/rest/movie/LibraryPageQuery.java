package hr.ferit.filmood.common.rest.movie;

import hr.ferit.filmood.common.rest.PageQuery;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class LibraryPageQuery extends PageQuery {

    @Min(0)
    @Max(5)
    private Integer userRating;

    public LibraryPageQuery() {
    }

    public LibraryPageQuery(@Nullable Integer page,
                            @Nullable Integer size,
                            @Nullable String sort,
                            @Nullable String direction,
                            @Nullable Integer userRating) {

        super(page, size, sort, direction);
        this.userRating = userRating;
    }
}
