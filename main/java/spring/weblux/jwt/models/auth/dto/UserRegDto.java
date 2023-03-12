package spring.weblux.jwt.models.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRegDto {
    private String password = "";
    private String username = "";
    private String email = "";
}

