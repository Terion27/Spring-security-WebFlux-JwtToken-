package spring.weblux.jwt.db.services;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import spring.weblux.jwt.db.repositories.UserRepository;
import spring.weblux.jwt.models.auth.dto.UserAuthDto;

@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserAuthDto> userLoginByUsername(String username) {
        return userRepository
                .findAuthByUsername(username)
                .switchIfEmpty(Mono.just(new UserAuthDto("", "", "", false)));
    }
}
