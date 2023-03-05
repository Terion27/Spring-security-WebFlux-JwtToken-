package spring.weblux.jwt.auth.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import spring.weblux.jwt.models.ReqResp.ReqResp;

@Service
public class AuthService {
    private final AuthUserDetailsService authUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AuthUserDetailsService authUserDetailsService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authUserDetailsService = authUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Mono<ResponseEntity<ReqResp<String>>> login(String userName, String password) {
        return authUserDetailsService
                .findByUsername(userName.toLowerCase())
                .filter(usr -> (passwordEncoder.matches(password, usr.getPassword())))
                .map(u -> ResponseEntity.ok(new ReqResp<>(jwtService.generate(u.getUsername()), "Success")))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReqResp<>("", "Credentials are incorrect"))));
    }
}