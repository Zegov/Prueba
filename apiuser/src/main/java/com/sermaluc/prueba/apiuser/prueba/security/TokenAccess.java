package com.sermaluc.prueba.apiuser.prueba.security;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sermaluc.prueba.apiuser.prueba.model.User;
import com.sermaluc.prueba.apiuser.prueba.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenAccess implements HandlerInterceptor {

    private UserRepository userRepository;
    private long tokenExpiration;

    public TokenAccess(UserRepository userRepository, @Value("${token.expiration}") long tokenExpiration) {
        this.userRepository = userRepository;
        this.tokenExpiration = tokenExpiration;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        String path = request.getRequestURI();
        if (path.equals("/api/users/register") ||
            path.startsWith("/swagger-ui") ||
            path.startsWith("/v3/api-docs") ||
            path.equals("/api-docs") ||
            path.startsWith("/swagger-resources") ||
            path.startsWith("/webjars")
            ) {
                return true; // Permitir la solicitud sin validar el token
        }

        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"mensaje\": \"Token no proporcionado\"}");
            return false;
        }

        Optional<User> user = userRepository.findAll().stream()
                .filter(u -> u.getToken().equals(token))
                .map(u -> (User) u)
                .findFirst();

        if (user.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"mensaje\": \"Token inválido\"}");
            return false;
        }
        LocalDateTime lastLogin = user.map(User::getUltimoLogin).orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
        if (lastLogin.plusSeconds(tokenExpiration).isBefore(LocalDateTime.now())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"mensaje\": \"Token expirado\"}");
            return false;
        }

        return true; // Permitir la solicitud si el token es válido
    }

}
