package com.huysor.saas.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String message = "Authentication failed";
        int status = HttpServletResponse.SC_UNAUTHORIZED;

        Throwable cause = authException.getCause();
        if (cause != null) {
            String causeMessage = cause.getMessage().toLowerCase();

            if (causeMessage.contains("expired")) {
                message = "Token has expired";
            } else if (causeMessage.contains("signature") || causeMessage.contains("invalid")) {
                message = "Invalid token signature";
            } else if (causeMessage.contains("jwt") || causeMessage.contains("malformed")) {
                message = "Malformed token";
            }
        } else {
            if (authException.getMessage().toLowerCase().contains("bearer")) {
                message = "Authorization invalid";
            }
        }

        response.setStatus(status);
        response.setContentType("application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("error", message);
        body.put("code", status);

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}

