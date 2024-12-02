package hr.ferit.filmood.common.rest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
public abstract class PageQuery {

    private final static int PAGE = 0;
    private final static int SIZE = 10;
    private final static String SORT = "createdAt";
    private final static String DIRECTION = "DESC";

    @NotNull
    protected Integer page;

    @NotNull
    @Min(1)
    protected Integer size;

    protected String sort;

    protected String direction;

    public PageQuery() {
        this(null, null, null, null);
    }

    public PageQuery(@Nullable Integer page, @Nullable Integer size,
                     @Nullable String sort, @Nullable String direction) {

        this.page = Objects.requireNonNullElse(page, PAGE);
        this.size = Objects.requireNonNullElse(size, SIZE);
        this.sort = Objects.requireNonNullElse(sort, SORT);
        this.direction = Objects.requireNonNullElse(direction, DIRECTION);
    }

    public void setPage(Integer page) {
        this.page = Objects.requireNonNullElse(page, this.page);
    }

    public void setSize(Integer size) {
        this.size = Objects.requireNonNullElse(size, this.size);
    }

    public PageQuery setSort(String sort) {
        this.sort = Objects.requireNonNullElse(sort, this.sort);
        return this;
    }

    public PageQuery setDirection(String direction) {
        this.direction = Objects.requireNonNullElse(direction, this.direction);
        return this;
    }

    public PageRequest toPageRequest() {
        return PageRequest.of(
                getPage(),
                getSize(),
                Sort.by(
                        Sort.Direction.valueOf(getDirection()),
                        getSort()
                )
        );
    }
}
