package spring.weblux.jwt.auth.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import spring.weblux.jwt.db.services.UserService;
import spring.weblux.jwt.models.auth.dto.UserAuthDto;

@Service
public class AccessDbService {
    private final UserService userService;

    public AccessDbService(UserService userService) {
        this.userService = userService;
    }

    public Mono<UserAuthDto> userDetailsByUsername(String username) {
        return userService.userLoginByUsername(username);
    }

}
