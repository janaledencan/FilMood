package hr.ferit.filmood.service;

import hr.ferit.filmood.persistence.entity.UserEntity;
import hr.ferit.filmood.persistence.repository.UserRepository;
import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;
import hr.ferit.filmood.rest.api.authentication.request.CreateUserRequest;
import hr.ferit.filmood.rest.api.authentication.response.LogoutResponse;
import hr.ferit.filmood.rest.api.authentication.response.SessionExpiredResponse;
import hr.ferit.filmood.service.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.securityContextRepository = securityContextRepository;
    }

    @Override
    public void authenticate(AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response) {
        try{
            UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(authRequest.username(),authRequest.password());
            Authentication authentication = authenticationManager.authenticate(authToken);
            if (authentication.isAuthenticated()) {
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);
            }
        }catch (Exception e){
            throw new BadCredentialsException(String.format("Failed authentication with username: %s", authRequest.username()));
        }
    }

    @Override
    public void signup(CreateUserRequest createUserRequest) {
        if(userRepository.findByUsername(createUserRequest.username()).isPresent()) {
            throw UserException.usernameAlreadyExists();
        }
        if(userRepository.findByEmail(createUserRequest.email().toLowerCase()).isPresent()) {
            throw UserException.emailAlreadyExists();
        }

        UserEntity user = new UserEntity(createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.username(),
                createUserRequest.email().toLowerCase(),
                createUserRequest.age(),
                bCryptPasswordEncoder.encode(createUserRequest.password()),
                createUserRequest.gender(),
                true);

        userRepository.save(user);
    }

    @Override
    public SessionExpiredResponse sessionExpired() {
        return new SessionExpiredResponse("Session has expired. Please log in again.");
    }

    @Override
    public LogoutResponse logoutSuccess() {
        return new LogoutResponse("Logged out.");
    }
}
