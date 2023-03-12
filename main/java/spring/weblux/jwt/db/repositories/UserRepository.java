package spring.weblux.jwt.db.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import spring.weblux.jwt.models.auth.dto.UserAuthDto;
import spring.weblux.jwt.models.db.User;


public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    @Query("SELECT users.username, users.password, users.role, users.status FROM users WHERE users.username = :username")
    Mono<UserAuthDto> findAuthByUsername(String username);

    @Query("SELECT users.username, users.password, users.role, users.status FROM users WHERE (users.username = :username or users.email = :email) limit 1")
    Mono<UserAuthDto> findAuthByUsernameEmail(String username, String email);
}
