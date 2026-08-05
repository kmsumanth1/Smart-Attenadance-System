package com.smartattendance.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartattendance.exception.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper;
    public RestAuthenticationEntryPoint(ObjectMapper mapper) { this.mapper = mapper; }
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), new ApiError(Instant.now(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized", "A valid JWT bearer token is required", Map.of()));
    }
}
