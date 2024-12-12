package hr.ferit.filmood.service.mapper;

import hr.ferit.filmood.persistence.entity.UserEntity;
import hr.ferit.filmood.rest.api.authentication.response.UserResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserResponse mapResponse(UserEntity user);
}
