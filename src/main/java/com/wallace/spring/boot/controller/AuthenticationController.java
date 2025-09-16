package com.wallace.spring.boot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallace.spring.boot.dto.AuthenticationRequestDTO;
import com.wallace.spring.boot.dto.RegisterRequestDTO;
import com.wallace.spring.boot.model.services.AuthenticationService;
import com.wallace.spring.boot.model.services.AuthenticationService.AuthenticationResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register") 
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        return ResponseEntity.ok(authenticationService.register(requestDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {
        return ResponseEntity.ok(authenticationService.authenticated(requestDTO));
    }
}