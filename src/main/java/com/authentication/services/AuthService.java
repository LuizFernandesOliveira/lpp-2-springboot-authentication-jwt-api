package com.authentication.services;

import com.authentication.utils.AccountCredentials;
import com.authentication.utils.JwtUtil;
import com.authentication.utils.Token;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public Token generateToken(AccountCredentials credentials) {
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
