package com.wallace.spring.boot.config.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wallace.spring.boot.dto.ErroResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        String authHeader = request.getHeader("Authorization");
        String message;
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            message = "Acesso negado: É necessário fazer login para acessar este recurso. " +
                     "Use o endpoint /api/v1/auth/authenticate para obter seu token de acesso.";
        } else {
            message = "Acesso negado: Token JWT inválido ou expirado. " +
                     "Faça login novamente para obter um novo token.";
        }
        
        ErroResponse errorResponse = new ErroResponse(
            LocalDateTime.now(),
            message,
            request.getRequestURI()
        );
        
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}