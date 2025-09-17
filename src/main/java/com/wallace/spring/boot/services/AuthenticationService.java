package com.wallace.spring.boot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wallace.spring.boot.dto.AuthenticationRequestDTO;
import com.wallace.spring.boot.dto.RegisterRequestDTO;
import com.wallace.spring.boot.enums.Role;
import com.wallace.spring.boot.exceptions.CredenciaisInvalidasException;
import com.wallace.spring.boot.exceptions.EmailJaExistenteException;
import com.wallace.spring.boot.exceptions.EmailNaoEncontradoException;
import com.wallace.spring.boot.model.entities.User;
import com.wallace.spring.boot.model.repository.UserRepository;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

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
        logger.info("Iniciando registro para o novo usuário com email: {}", requestDTO.email());
        
        if (userRepository.findByEmail(requestDTO.email()).isPresent()) {
            logger.warn("Tentativa de registro com email já existente: {}", requestDTO.email());
            throw new EmailJaExistenteException("Este email já está cadastrado no sistema.");
        }
        
        try {
            User user = User.builder()
                    .username(requestDTO.nome())
                    .sobrenome(requestDTO.sobreNome())
                    .email(requestDTO.email())
                    .password(passwordEncoder.encode(requestDTO.senha()))
                    .roles(Role.USER)
                    .build();
            
            User savedUser = userRepository.save(user);
            String jwtToken = jwtService.generateToken(savedUser);
            logger.info("Usuário com email {} registrado com sucesso.", savedUser.getEmail());

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
                    
        } catch (Exception e) {
            logger.error("Erro interno durante registro para email {}: {}", requestDTO.email(), e.getMessage());
            throw new RuntimeException("Erro interno durante o registro. Tente novamente.");
        }
    }

    public AuthenticationResponse authenticated(AuthenticationRequestDTO requestDTO) {
        logger.info("Iniciando autenticação para o usuário com email: {}", requestDTO.email());
        
        try {
            User user = userRepository.findByEmail(requestDTO.email())
                    .orElseThrow(() -> {
                        logger.warn("Tentativa de autenticação com email não encontrado: {}", requestDTO.email());
                        return new EmailNaoEncontradoException("Email não encontrado no sistema.");
                    });
            
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.email(), 
                            requestDTO.senha()
                    )
            );

            String jwtToken = jwtService.generateToken(user);
            logger.info("Usuário com email {} autenticado com sucesso.", user.getEmail());

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
                    
        } catch (EmailNaoEncontradoException e) {
            throw e;
        } catch (BadCredentialsException e) {
            logger.warn("Tentativa de autenticação com credenciais inválidas para email: {}", requestDTO.email());
            throw new CredenciaisInvalidasException("Email ou senha incorretos.");
        } catch (AuthenticationException e) {
            logger.warn("Falha na autenticação para email {}: {}", requestDTO.email(), e.getMessage());
            throw new CredenciaisInvalidasException("Email ou senha incorretos.");
        } catch (Exception e) {
            logger.error("Erro interno durante autenticação para email {}: {}", requestDTO.email(), e.getMessage());
            throw new RuntimeException("Erro interno durante a autenticação. Tente novamente.");
        }
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