package hr.ferit.filmood.service.utils;

import hr.ferit.filmood.persistence.entity.UserEntity;
import org.springframework.security.core.Authentication;

public interface UserUtils {

    UserEntity getCurrentUser(Authentication authentication);
}
