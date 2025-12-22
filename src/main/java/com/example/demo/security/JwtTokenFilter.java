package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) 
            throws ServletException, IOException {
        
        // Skip authentication for public endpoints
        String path = request.getServletPath();
        if (path.startsWith("/auth/") || 
            path.startsWith("/swagger-ui/") || 
            path.startsWith("/v3/api-docs/") ||
            path.equals("/hello-servlet")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // For /api endpoints, check for token
        if (path.startsWith("/api/")) {
            String token = resolveToken(request);
            if (token == null || !token.startsWith("dummy-token-")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}