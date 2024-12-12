package hr.ferit.filmood.persistence.repository;

import hr.ferit.filmood.persistence.entity.MovieEntity;
import hr.ferit.filmood.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {

    List<MovieEntity> findAllByUser(UserEntity user);
}
