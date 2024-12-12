package hr.ferit.filmood.service;

import hr.ferit.filmood.persistence.entity.UserEntity;
import hr.ferit.filmood.persistence.repository.UserRepository;
import hr.ferit.filmood.rest.api.authentication.request.AuthRequest;
import hr.ferit.filmood.rest.api.authentication.request.CreateUpdateUserRequest;
import hr.ferit.filmood.rest.api.authentication.response.LogoutResponse;
import hr.ferit.filmood.rest.api.authentication.response.SessionExpiredResponse;
import hr.ferit.filmood.service.exception.UserException;
import hr.ferit.filmood.service.utils.UserUtils;
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
    private final UserUtils userUtils;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder, SecurityContextRepository securityContextRepository, UserUtils userUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.securityContextRepository = securityContextRepository;
        this.userUtils = userUtils;
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
    public void signup(CreateUpdateUserRequest createUpdateUserRequest) {

        checkUsernameAndEmailUniqueness(createUpdateUserRequest.username(), createUpdateUserRequest.email().toLowerCase());

        if(createUpdateUserRequest.password().isBlank()) {
            throw UserException.blankPassword();
        }

        UserEntity user = new UserEntity(createUpdateUserRequest.firstName(),
                createUpdateUserRequest.lastName(),
                createUpdateUserRequest.username(),
                createUpdateUserRequest.email().toLowerCase(),
                createUpdateUserRequest.age(),
                bCryptPasswordEncoder.encode(createUpdateUserRequest.password()),
                createUpdateUserRequest.gender(),
                true);

        userRepository.save(user);
    }

    @Override
    public void update(CreateUpdateUserRequest createUpdateUserRequest, Authentication authentication) {

        checkUsernameAndEmailUniqueness(createUpdateUserRequest.username(), createUpdateUserRequest.email().toLowerCase());

        UserEntity currentUser = userUtils.getCurrentUser(authentication);

        currentUser.setUsername(createUpdateUserRequest.username());
        if(!createUpdateUserRequest.password().isBlank()) {
            currentUser.setPassword(bCryptPasswordEncoder.encode(createUpdateUserRequest.password()));
        }
        currentUser.setAge(createUpdateUserRequest.age());
        currentUser.setGender(createUpdateUserRequest.gender());
        currentUser.setEmail(createUpdateUserRequest.email().toLowerCase());
        currentUser.setFirstName(createUpdateUserRequest.firstName());
        currentUser.setLastName(createUpdateUserRequest.lastName());

        userRepository.save(currentUser);
    }

    @Override
    public SessionExpiredResponse sessionExpired() {
        return new SessionExpiredResponse("Session has expired. Please log in again.");
    }

    @Override
    public LogoutResponse logoutSuccess() {
        return new LogoutResponse("Logged out.");
    }

    private void checkUsernameAndEmailUniqueness(String username, String email) {
        if(userRepository.findByUsername(username).isPresent()) {
            throw UserException.usernameAlreadyExists();
        }
        if(userRepository.findByEmail(email).isPresent()) {
            throw UserException.emailAlreadyExists();
        }
    }
}
