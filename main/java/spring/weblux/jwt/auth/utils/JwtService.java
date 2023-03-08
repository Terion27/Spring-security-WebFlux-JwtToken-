package spring.weblux.jwt.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtService {

    private static final int TOKEN_EXPIRATION_DATE = 7;
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
                .setExpiration(Date.from(Instant.now().plus(TOKEN_EXPIRATION_DATE, ChronoUnit.DAYS)))
                .signWith(key)
                .compact();
    }

    public String getUserName(String token) {
        return parser
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean isValid(String token, String username) {
        Claims claims = parser
                .parseClaimsJws(token).
                getBody();
        boolean unexpired = claims
                .getExpiration()
                .after(Date.from(Instant.now()));

        return unexpired && username.equalsIgnoreCase(claims.getSubject());
    }
}
