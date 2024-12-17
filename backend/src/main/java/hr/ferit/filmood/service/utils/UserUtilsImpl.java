package hr.ferit.filmood.service.utils;

import hr.ferit.filmood.persistence.entity.UserEntity;
import hr.ferit.filmood.persistence.repository.UserRepository;
import hr.ferit.filmood.service.exception.UserException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserUtilsImpl implements UserUtils {

    private final UserRepository userRepository;

    public UserUtilsImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getCurrentUser(Authentication authentication) {

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(UserException::notFound);
    }
}
