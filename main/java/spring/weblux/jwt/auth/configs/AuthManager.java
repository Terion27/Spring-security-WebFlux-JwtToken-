package spring.weblux.jwt.auth.configs;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import spring.weblux.jwt.auth.utils.AuthUserDetailsService;
import spring.weblux.jwt.auth.utils.JwtService;
import spring.weblux.jwt.models.auth.BearerToken;

@Component
@Log4j2
public class AuthManager implements ReactiveAuthenticationManager {
    final JwtService jwtService;
    final AuthUserDetailsService authUserDetailsService;

    public AuthManager(JwtService jwtService, AuthUserDetailsService authUserDetailsService) {
        this.jwtService = jwtService;
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        return Mono.justOrEmpty(
                authentication
        )
                .cast(BearerToken.class)
                .flatMap(auth -> {
                    String userName = jwtService.getUserName(auth.getCredentials());
                    Mono<UserDetails> user = authUserDetailsService.findByUsername(userName).switchIfEmpty(Mono.error(new Exception("User not found")));
                    return user.flatMap(u -> {
                        if (u.getUsername() == null) {
                            log.info("User not found");
                            return Mono.error(new Exception("User not found"));
                        }
                        if (jwtService.isValid(auth.getCredentials(), u.getUsername())) {
                            return Mono.just(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities()));
                        }
                        log.info("Invalid / Expired Token : {}", auth.getCredentials());
                        return Mono.error(new Exception("Invalid/Expired Token"));
                    });
                });
    }

}
