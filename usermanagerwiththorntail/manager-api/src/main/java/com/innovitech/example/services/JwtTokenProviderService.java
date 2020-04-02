package com.innovitech.example.services;

public interface JwtTokenProviderService {
     String createToken(String username);

     boolean validateToken(String token);
}
