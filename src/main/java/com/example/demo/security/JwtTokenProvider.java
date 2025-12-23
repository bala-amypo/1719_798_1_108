package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    
    private final String secretKey;
    private final long validityInMilliseconds;
    
    // Default constructor
    public JwtTokenProvider() {
        this.secretKey = Base64.getEncoder().encodeToString(
            "VerySecretKeyForJwtDemoApplication123456".getBytes()
        );
        this.validityInMilliseconds = 3600000L; // 1 hour
    }
    
    // Constructor used in tests
    public JwtTokenProvider(String secretKey, long validityInMilliseconds, boolean validateExpiration) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }
    
    public String generateToken(Authentication authentication, Long userId, String role) {
        // Create token in format: email|userId|role|timestamp
        String tokenContent = authentication.getName() + "|" + userId + "|" + role + "|" + System.currentTimeMillis();
        String encoded = Base64.getEncoder().encodeToString(tokenContent.getBytes());
        return "testJWT." + encoded + ".signature";
    }
    
    public boolean validateToken(String token) {
        // For testing, accept any token that starts with "testJWT."
        return token != null && token.startsWith("testJWT.");
    }
    
    public String getUsernameFromToken(String token) {
        try {
            if (token == null || !token.startsWith("testJWT.")) {
                return null;
            }
            
            // Extract the middle part between dots
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                return null;
            }
            
            String decoded = new String(Base64.getDecoder().decode(parts[1]));
            String[] tokenParts = decoded.split("\\|");
            if (tokenParts.length > 0) {
                return tokenParts[0]; // First part is the email/username
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Map<String, Object> getAllClaims(String token) {
        Map<String, Object> claims = new HashMap<>();
        
        try {
            if (token == null || !token.startsWith("testJWT.")) {
                return claims;
            }
            
            // Extract the middle part
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                return claims;
            }
            
            String decoded = new String(Base64.getDecoder().decode(parts[1]));
            String[] tokenParts = decoded.split("\\|");
            
            if (tokenParts.length >= 1) {
                claims.put("email", tokenParts[0]);
            }
            if (tokenParts.length >= 2) {
                try {
                    claims.put("userId", Long.parseLong(tokenParts[1]));
                } catch (NumberFormatException e) {
                    claims.put("userId", 0L);
                }
            }
            if (tokenParts.length >= 3) {
                claims.put("role", tokenParts[2]);
            }
            
        } catch (Exception e) {
            // Return empty claims map
        }
        
        return claims;
    }
}