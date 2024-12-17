package hr.ferit.filmood.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static hr.ferit.filmood.common.CommonConstants.EMAIL_REGEX;
import static hr.ferit.filmood.common.CommonConstants.GENDER_REGEX;
import static hr.ferit.filmood.common.CommonConstants.PASSWORD_REGEX;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@Entity
@Table(name = "user_account", uniqueConstraints = {
        @UniqueConstraint(name = "UQ__USERNAME", columnNames = "username"),
        @UniqueConstraint(name = "UQ__EMAIL", columnNames = "email")
        })
public class UserEntity extends AbstractEntity {

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @EqualsAndHashCode.Include
    @Column(name = "username")
    private String username;

    @NotBlank
    @Pattern(regexp = EMAIL_REGEX)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Pattern(regexp = GENDER_REGEX)
    @Column(name = "gender")
    private String gender;

    @NotNull
    @Min(0)
    @Max(120)
    @Column(name = "age")
    private Integer age;

    @NotBlank
    @Pattern(regexp = PASSWORD_REGEX)
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "active")
    private Boolean isActive;

    public UserEntity(String firstName, String lastName, String username,
                      String email, Integer age, String password, String gender, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.age = age;
        this.password = password;
        this.gender = gender;
        this.isActive = isActive;
    }
}
