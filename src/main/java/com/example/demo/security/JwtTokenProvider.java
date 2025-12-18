package com.example.demo.security;

public class JwtTokenProvider {

    private String secret;
    private long validityInMs;
    private boolean someFlag;

    public JwtTokenProvider(String secret, long validityInMs, boolean someFlag) {
        this.secret = secret;
        this.validityInMs = validityInMs;
        this.someFlag = someFlag;
    }

    public String createToken(String subject) {
        return "dummy-jwt-token";
    }

    public boolean validateToken(String token) {
        return true;
    }
}
