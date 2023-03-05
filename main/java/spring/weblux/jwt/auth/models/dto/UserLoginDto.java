package spring.weblux.jwt.auth.models.dto;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserLoginDto {
    private String password;
    private String username;
}

