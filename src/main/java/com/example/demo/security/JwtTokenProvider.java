package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    
    private final SecretKey key;
    private final long validityInMs;
    private final boolean someFlag;
    
    public JwtTokenProvider(String secret, long validityInMs, boolean someFlag) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMs = validityInMs;
        this.someFlag = someFlag;
    }
    
    public String generateToken(Authentication authentication, Long userId, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMs);
        
        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim("userId", userId)
            .claim("role", role)
            .claim("authorities", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
    
    public String generateToken(Authentication authentication, long validityInMs, String role) {
        return generateToken(authentication, 0L, role);
    }
    
    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}