package com.example.demo.security;

import com.example.demo.entity.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "secret123";
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;

    public String generateToken(Long userId, String email, String role, String department) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        claims.put("department", department);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateTokenForUser(User user) {
        return generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getDepartment()
        );
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
    }

    public String extractUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public Long extractUserId(String token) {
        return parseToken(token).getBody().get("userId", Long.class);
    }

    public String extractRole(String token) {
        return parseToken(token).getBody().get("role", String.class);
    }

    public boolean isTokenValid(String token, String email) {
        return extractUsername(token).equals(email)
                && !parseToken(token).getBody()
                .getExpiration()
                .before(new Date());
    }
}
