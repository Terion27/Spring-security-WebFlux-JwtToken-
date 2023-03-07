package spring.weblux.jwt.auth.controllers;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import spring.weblux.jwt.models.auth.dto.UserLoginDto;
import spring.weblux.jwt.auth.services.AuthService;
import spring.weblux.jwt.models.auth.AuthReqResp.AuthReqResp;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/reg")
    public Mono<ResponseEntity<AuthReqResp<Boolean>>> reg() {
        return Mono.just(ResponseEntity.ok().body(new AuthReqResp<>(true, "")));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthReqResp<String>>> login(@RequestBody UserLoginDto userLogin) {
        return authService.login(userLogin.getUsername(), userLogin.getPassword());
    }

    @GetMapping("/valid")
    Mono<ResponseEntity<Boolean>> val() {
        return Mono.just(ResponseEntity.ok(true));
    }

}