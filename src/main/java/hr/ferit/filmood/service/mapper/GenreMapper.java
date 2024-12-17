package hr.ferit.filmood.service.mapper;

import hr.ferit.filmood.persistence.entity.GenreEntity;
import hr.ferit.filmood.rest.api.genre.dto.GenreDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface GenreMapper {

    @Mapping(source = "id", target = "genreId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    GenreEntity mapEntity(GenreDTO genreDTO);

    default List<GenreEntity> mapEntity(List<GenreDTO> content) {
        if (Objects.isNull(content)) {
            return new ArrayList<>();
        }
        return content.stream().map(this::mapEntity).toList();
    }
}
