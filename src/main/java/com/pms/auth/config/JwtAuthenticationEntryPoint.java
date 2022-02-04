package com.pms.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.auth.dto.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String message;
        final Exception exception = (Exception) request.getAttribute("exception");

        if (exception != null) {
            byte[] body = new ObjectMapper().writeValueAsBytes(new ErrorResponse("true", exception.getMessage()));

            response.getOutputStream().write(body);
        } else {
            if (e.getCause() != null) {
                message = e.getCause().toString() + " " + e.getMessage();
            } else {
                message = e.getMessage();
            }

            byte[] body = new ObjectMapper().writeValueAsBytes(new ErrorResponse("true", message));

            response.getOutputStream().write(body);
        }
    }
}
