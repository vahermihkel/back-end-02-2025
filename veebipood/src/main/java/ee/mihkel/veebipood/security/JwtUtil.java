package ee.mihkel.veebipood.security;

import ee.mihkel.veebipood.entity.Person;
import ee.mihkel.veebipood.model.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String superSecretKey = "hG1hwE+fQX/SbyTM2OqrGHTREyN+v/o5BVpjS5s+r0I=";

    private final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(superSecretKey));

    public AuthToken generateToken(Person person) {

        // 3h
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3);

        String token = Jwts.builder()
                .signWith(key)
                .subject(person.getId().toString())
                .claim("role", person.getRole())
                .issuedAt(new Date())
                .expiration(expiration)
                .compact();

        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setExpiration(expiration);
        return authToken;
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
