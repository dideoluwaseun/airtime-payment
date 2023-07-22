package com.oluwaseun.airtimepayment.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oluwaseun.airtimepayment.dto.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Set the content type of the response to indicate it's JSON
        response.setContentType("application/json");

        // Build the custom error response
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.setDetails("Unauthorized");
        errorResponse.setMessage("Invalid or missing JWT token");
        errorResponse.setTimestamp(Calendar.getInstance().getTime());

        // Convert the error response to JSON and write it to the response body
        String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonResponse);
    }
}
