package hr.ferit.filmood.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "genre")
public class GenreEntity extends AbstractEntity {

    @NotBlank
    @Column(name = "name")
    private String name;

    @Column(name = "genre_id")
    private Integer genreId;
}
