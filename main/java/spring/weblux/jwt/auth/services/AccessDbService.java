package spring.weblux.jwt.auth.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import spring.weblux.jwt.db.services.UserService;
import spring.weblux.jwt.models.auth.dto.UserAuthDto;
import spring.weblux.jwt.models.auth.dto.UserRegDto;

@Service
public class AccessDbService {
    private final UserService userService;

    public AccessDbService(UserService userService) {
        this.userService = userService;
    }

    public Mono<UserAuthDto> userDetailsByUsername(String username) {
        return userService.userLoginByUsername(username);
    }

    public Mono<UserAuthDto> createUser(UserRegDto userReg) {
        return userService.createUser(userReg);
    }
}
