package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    
    private final String secret;
    private final long validityInMs;
    private final boolean someFlag;
    
    public JwtTokenProvider(String secret, long validityInMs, boolean someFlag) {
        this.secret = secret;
        this.validityInMs = validityInMs;
        this.someFlag = someFlag;
    }
    
    // Empty class - just meets constructor requirement
}