package hr.ferit.filmood.common.rest;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageDTO {

    @NotNull
    private final Integer number;

    @NotNull
    private final Integer size;

    @NotNull
    private final Integer totalPages;

    @NotNull
    private final Long totalElements;

    public PageDTO(Integer number, Integer size, Integer totalPages, Long totalElements) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public static PageDTO from(Page<?> page) {
        return new PageDTO(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
