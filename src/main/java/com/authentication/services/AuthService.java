package com.authentication.services;

import com.authentication.utils.AccountCredentials;
import com.authentication.utils.JwtUtil;
import com.authentication.utils.Token;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {

    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    public AuthService(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public Token generateToken(AccountCredentials credentials) {
        System.out.println("=======================Entrou no service");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            credentials.getPassword()));

        } catch (Exception e) {
            throw new NoSuchElementException("Invalide email or password");
        }

        String token = jwtUtil.generateToken(credentials.getEmail());
        return Token.builder().token(token).build();
    }

}
