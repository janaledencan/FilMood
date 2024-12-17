package hr.ferit.filmood.common.rest;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PagedResponse<R> {

    protected PageDTO page;
    protected List<R> content;

    public PagedResponse(PageDTO page, List<R> content) {
        this.page = page;
        this.content = content;
    }

    public PagedResponse<R> of(Page<R> page) {
        return new PagedResponse<>(
                new PageDTO(
                        page.getNumber(),
                        page.getSize(),
                        page.getTotalPages(),
                        page.getTotalElements()
                ),
                page.getContent()
        );
    }
}
