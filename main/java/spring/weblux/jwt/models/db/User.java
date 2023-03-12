package spring.weblux.jwt.models.db;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("users")
public class User {

    @Id
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    private String telephone;
    @Column("registration_date")
    private LocalDateTime registrationDate;
    private boolean status;
    private boolean visibility;
    private String role;
}
