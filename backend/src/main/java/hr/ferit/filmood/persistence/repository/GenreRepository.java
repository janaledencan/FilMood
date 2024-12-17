package hr.ferit.filmood.persistence.repository;

import hr.ferit.filmood.persistence.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<GenreEntity, UUID> {
}
