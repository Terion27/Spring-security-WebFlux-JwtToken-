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
import spring.weblux.jwt.models.auth.dto.UserLoginDto;
import spring.weblux.jwt.models.auth.dto.UserRegDto;

@Service
public class AuthService {
    private final AuthUserDetailsService authUserDetailsService;
    private final AccessDbService accessDbService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AuthUserDetailsService authUserDetailsService, AccessDbService accessDbService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authUserDetailsService = authUserDetailsService;
        this.accessDbService = accessDbService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Mono<ResponseEntity<AuthReqResp<String>>> login(UserLoginDto userLogin) {
        return authUserDetailsService
                .findByUsername(userLogin.getUsername().toLowerCase())
                .filter(u -> u.getUsername().equals(userLogin.getUsername()) && passwordEncoder.matches(userLogin.getPassword(), u.getPassword()))
                .filter(UserDetails::isEnabled)  // is Status optional
                .map(u -> ResponseEntity.ok(new AuthReqResp<>(jwtService.generate(u.getUsername()), "Success")))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthReqResp<>("", "Credentials are incorrect")));
    }

    public Mono<ResponseEntity<AuthReqResp<String>>> reg(UserRegDto userReg) {
        if (!(userReg.getUsername().isEmpty() || userReg.getPassword().isEmpty() || userReg.getEmail().isEmpty())) {
            return accessDbService.createUser(userReg)
                    .map(u -> {
                        if (u.getId() == 0)
                            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new AuthReqResp<>("", "Login or email is incorrect"));
                        return ResponseEntity.ok(new AuthReqResp<>("OK", "User " + u.getId() + " is registered"));
                    })
                    .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthReqResp<>("", "BAD_REQUEST")));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthReqResp<>("", "Fields must not be empty")));
    }
}