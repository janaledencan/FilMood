package hr.ferit.filmood.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "genre")
public class MovieEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK__MOVIE__USER_ID"))
    private UserEntity user;

    @Column(name = "title")
    private String title;

    @Column(name = "genres")
    private String genres;

    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "vote_average")
    private Float voteAverage;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "user_rating")
    private Integer userRating;
}
