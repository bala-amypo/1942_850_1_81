package com.example.demo.security;

import com.example.demo.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey12";
    private static final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    private final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    /* ======================================================
       INNER CLASS (NO NEW FILE)
       ====================================================== */
    public static class ParsedToken {
        private final Claims claims;

        public ParsedToken(Claims claims) {
            this.claims = claims;
        }

        // 🔥 THIS IS WHAT TESTS EXPECT
        public Claims getPayload() {
            return claims;
        }
    }

    /* ================= TOKEN GENERATION ================= */

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION)
                )
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    public String generateTokenForUser(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return generateToken(claims, user.getEmail());
    }

    /* ================= TOKEN PARSING ================= */

    // ✅ RETURN INNER CLASS (NOT Jws / Jwt)
    public ParsedToken parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();

        return new ParsedToken(claims);
    }

    public String extractUsername(String token) {
        return parseToken(token).getPayload().getSubject();
    }

    public String extractRole(String token) {
        return (String) parseToken(token).getPayload().get("role");
    }

    public Long extractUserId(String token) {
        Object id = parseToken(token).getPayload().get("userId");
        return id == null ? null : Long.valueOf(id.toString());
    }

    public boolean isTokenValid(String token, String username) {
        try {
            return extractUsername(token).equals(username);
        } catch (Exception e) {
            return false;
        }
    }
}
