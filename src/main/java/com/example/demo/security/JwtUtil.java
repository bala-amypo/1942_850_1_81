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

    // ===== REQUIRED BY TESTS =====
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // ===== USED BY SERVICES =====
    public String generateToken(Long userId, String email, String role, String department) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);      // 🔥 REQUIRED
        claims.put("role", role);        // 🔥 REQUIRED
        claims.put("department", department);

        return generateToken(claims, email); // subject = email
    }

    public String generateTokenForUser(User user) {
        return generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getDepartment()
        );
    }
    public TokenWrapper parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return new TokenWrapper(claims);
    }

    public String extractUsername(String token) {
        return parseToken(token).getPayload().getSubject();
    }

    public Long extractUserId(String token) {
        return parseToken(token).getPayload().get("userId", Long.class);
    }

    public String extractRole(String token) {
        return parseToken(token).getPayload().get("role", String.class);
    }

    public boolean isTokenValid(String token, String email) {
        Claims claims = parseToken(token).getPayload();
        return claims.getSubject().equals(email)
                && claims.getExpiration().after(new Date());
    }

    public static class TokenWrapper {
        private final Claims payload;

        public TokenWrapper(Claims payload) {
            this.payload = payload;
        }

        public Claims getPayload() {
            return payload;
        }
    }
}
