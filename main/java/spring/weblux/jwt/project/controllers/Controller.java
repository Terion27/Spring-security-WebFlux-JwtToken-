package spring.weblux.jwt.project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

    @GetMapping("/")
    public Mono<String> index() {
        return Mono.just("Index page!");
    }
}