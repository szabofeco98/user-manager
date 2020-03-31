package com.innovitech.example.services;

public interface JwtTokenProviderService {
     String createToken(String username, String role);

     String getRole(String token);

     String getUsername(String token);

     boolean validateToken(String token);
}
