package spring.weblux.jwt.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import spring.weblux.jwt.models.auth.dto.UserLoginDto;
import spring.weblux.jwt.auth.services.AuthService;
import spring.weblux.jwt.models.auth.AuthReqResp.AuthReqResp;
import spring.weblux.jwt.models.auth.dto.UserRegDto;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/reg")
    public Mono<ResponseEntity<AuthReqResp<String>>> reg(@RequestBody UserRegDto userReg) {
        return authService.reg(userReg);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthReqResp<String>>> login(@RequestBody UserLoginDto userLogin) {
        return authService.login(userLogin);
    }

    @GetMapping("/valid")
    Mono<ResponseEntity<Boolean>> val() {
        return Mono.just(ResponseEntity.ok(true));
    }

}