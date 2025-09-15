package com.wallace.spring.boot.model.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wallace.spring.boot.dto.AuthenticationRequestDTO;
import com.wallace.spring.boot.dto.RegisterRequestDTO;
import com.wallace.spring.boot.enums.Role;
import com.wallace.spring.boot.model.entities.User;
import com.wallace.spring.boot.model.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequestDTO requestDTO) {
        User user = User.builder()
                .username(requestDTO.nome())
                .sobrenome(requestDTO.sobreNome())
                .email(requestDTO.email())
                .password(passwordEncoder.encode(requestDTO.senha()))
                .roles(Role.USER)
                .build();
        
        User savedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(savedUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticated(AuthenticationRequestDTO requestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.email(), 
                        requestDTO.senha()
                )
        );

        User user = userRepository.findByEmail(requestDTO.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public static class AuthenticationResponse {
        private String token;

        public AuthenticationResponse(String token) {
            this.token = token;
        }

        public AuthenticationResponse() {}

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static AuthenticationResponseBuilder builder() {
            return new AuthenticationResponseBuilder();
        }

        public static class AuthenticationResponseBuilder {
            private String token;

            public AuthenticationResponseBuilder token(String token) {
                this.token = token;
                return this;
            }

            public AuthenticationResponse build() {
                return new AuthenticationResponse(token);
            }
        }
    }
}