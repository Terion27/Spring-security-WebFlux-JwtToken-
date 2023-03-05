package spring.weblux.jwt.auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import spring.weblux.jwt.auth.models.dto.UserAuthDto;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;
    private final JwtParser parser;


    public JwtService(@Value("${jwt_secret}") String _key) {
        key = Keys.hmacShaKeyFor(_key.getBytes(StandardCharsets.UTF_8));
        parser = Jwts.parserBuilder().setSigningKey(this.key).build();
    }

    public String generate(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .signWith(key)
                .compact();
    }

    public String getUserName(String token) {
        return parser
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean isValid(String token, UserAuthDto userAuthDto) {
        Claims claims = parser
                .parseClaimsJws(token).
                getBody();
        boolean unexpired = claims
                .getExpiration()
                .after(Date.from(Instant.now()));

        return unexpired && userAuthDto.getUsername().equalsIgnoreCase(claims.getSubject());
    }
}
