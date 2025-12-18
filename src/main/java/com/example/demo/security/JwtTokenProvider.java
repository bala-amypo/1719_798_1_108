package com.example.demo.security;

public class JwtTokenProvider {

    private final String secret;
    private final long validityInMs;
    private final boolean someFlag;

    public JwtTokenProvider(String secret, long validityInMs, boolean someFlag) {
        this.secret = secret;
        this.validityInMs = validityInMs;
        this.someFlag = someFlag;
    }
}
