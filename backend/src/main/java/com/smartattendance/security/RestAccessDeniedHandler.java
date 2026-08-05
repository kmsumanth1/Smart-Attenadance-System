package com.smartattendance.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartattendance.exception.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper mapper;
    public RestAccessDeniedHandler(ObjectMapper mapper) { this.mapper = mapper; }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), new ApiError(Instant.now(), HttpStatus.FORBIDDEN.value(), "Forbidden", "You do not have permission to access this resource", Map.of()));
    }
}
