package hr.ferit.filmood.service.security;

import hr.ferit.filmood.persistence.entity.UserEntity;
import hr.ferit.filmood.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        UserEntity retrievedUser = userEntity.get();

        return new UserDetailsImpl(retrievedUser);
    }
}
