package com.wallace.spring.boot.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wallace.spring.boot.dto.ErroResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                      AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        String message;
        
        if (endpoint.contains("/clientes") && ("PUT".equals(method) || "DELETE".equals(method))) {
            message = "Acesso negado: Apenas administradores podem alterar ou excluir dados de clientes. " +
                     "Seu perfil atual não possui as permissões necessárias para esta operação.";
        } else {
            message = "Acesso negado: Você não possui permissão para acessar este recurso. " +
                     "Entre em contato com um administrador se acredita que deveria ter acesso.";
        }
        
        ErroResponse errorResponse = new ErroResponse(
            LocalDateTime.now(),
            message,
            endpoint
        );
        
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}