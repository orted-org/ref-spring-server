package orted.firebasejwt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import org.springframework.stereotype.Service;
import orted.firebasejwt.entities.CustomUserForContext;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "kC7nS4jKc3RqtwCN3ZYz7ttdPTf2AiSvi3VnNUxGkLtfi3wHJg9AmpyBZcpwLPud4DbF2ug8exePd3X9YAUtdWJA8EvgJ92nBJQV";

    private static final String USERINFO = "USERINFO";

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .deserializeJsonWith(new JacksonDeserializer(Maps.of(USERINFO, CustomUserForContext.class).build())) // <-----
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(CustomUserForContext customUserForContext) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERINFO, customUserForContext);
        return createToken(claims, customUserForContext.getPhone());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        ObjectMapper objectMapper = new ObjectMapper();
        return Jwts.builder()
                .setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public CustomUserForContext validateToken(String token) throws Exception  {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .deserializeJsonWith(new JacksonDeserializer(Maps.of(USERINFO, CustomUserForContext.class).build()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(USERINFO, CustomUserForContext.class);
    }
}

