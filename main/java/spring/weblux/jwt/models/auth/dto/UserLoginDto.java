package spring.weblux.jwt.models.auth.dto;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserLoginDto {
    private String password;
    private String username;

    public UserLoginDto() {
    }

    public UserLoginDto(String password, String username) {
        this.password = password;
        this.username = username;
    }
}

