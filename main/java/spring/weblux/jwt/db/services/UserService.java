package spring.weblux.jwt.db.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import spring.weblux.jwt.db.repositories.UserRepository;
import spring.weblux.jwt.models.auth.dto.UserAuthDto;
import spring.weblux.jwt.models.auth.dto.UserRegDto;
import spring.weblux.jwt.models.db.User;

import java.time.LocalDateTime;

@Slf4j
@Component
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<UserAuthDto> userLoginByUsername(String username) {
        return userRepository
                .findAuthByUsername(username)
                .switchIfEmpty(Mono.just(new UserAuthDto(0L, "", "", "", false)));
    }

    public Mono<UserAuthDto> createUser(UserRegDto userReg) {
        return userRepository
                .findAuthByUsernameEmail(userReg.getUsername(), userReg.getEmail())
                .map(u -> new UserAuthDto(0L, "", "", "", false))
                .switchIfEmpty(userRepository.save(new User().toBuilder()
                                .username(userReg.getUsername())
                                .password(passwordEncoder.encode(userReg.getPassword()))
                                .email(userReg.getEmail())
                                .registrationDate(LocalDateTime.now())
                                .status(true)
                                .visibility(true)
                                .role("ROLE_USER")
                                .build())
                        .map(u -> new UserAuthDto(u.getId(), u.getUsername(), u.getPassword(), u.getRole(), u.isStatus()))
                        .doOnSuccess(u -> log.info("Created new user with ID = " + u.getId())));
    }
}
