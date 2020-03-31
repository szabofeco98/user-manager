package com.innovitech.example.serviceImpl;


import com.innovitech.example.services.JwtTokenProviderService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.inject.Singleton;
import java.util.Base64;
import java.util.Date;

@Default
@Singleton
public class JwtTokenProvider implements JwtTokenProviderService {

    private String secretKey="secret";

    private long validityInMilliseconds=3600000; // 1h

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getRole(String token){
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("auth");
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
