package spring.weblux.jwt.auth.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import spring.weblux.jwt.auth.utils.AuthUserDetailsService;
import spring.weblux.jwt.auth.utils.JwtService;
import spring.weblux.jwt.models.auth.AuthReqResp.AuthReqResp;

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

    public Mono<ResponseEntity<AuthReqResp<String>>> login(String userName, String password) {
        return authUserDetailsService
                .findByUsername(userName.toLowerCase())
                .filter(u -> u.getUsername().equals(userName) && passwordEncoder.matches(password, u.getPassword()))
                .filter(UserDetails::isEnabled)  // is Status optional
                .map(u -> ResponseEntity.ok(new AuthReqResp<>(jwtService.generate(u.getUsername()), "Success")))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthReqResp<>("", "Credentials are incorrect")));
    }
}