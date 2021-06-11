package com.authentication.controllers;

import com.authentication.services.AuthService;
import com.authentication.utils.AccountCredentials;
import com.authentication.utils.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/token")
    public ResponseEntity<Token> generateToken(@RequestBody AccountCredentials credentials) {
        Token token = authService.generateToken(credentials);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);

    }

}
