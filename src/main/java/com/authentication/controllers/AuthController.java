package com.authentication.controllers;

import com.authentication.utils.AccountCredentials;
import com.authentication.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "/token")
    public String generateToken(@RequestBody AccountCredentials credentials) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            credentials.getPassword()));

        } catch (Exception e) {
            throw new Exception("Invalidate email or passowrd");
        }

        return jwtUtil.generateToken(credentials.getEmail());
    }

}
