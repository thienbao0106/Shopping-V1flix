package com.example.demo.Auth;

import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.ResponseType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.Filter;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;


public class RequestResponseLoggingFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if necessary
        System.out.println("Checked");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;


        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(403);
                response.setContentType("application/json");

                // Create a JSON response object
                ResponseHeader responseHeader = new ResponseHeader(
                        LocalDateTime.now(),
                        "PERMISSION_ERROR",
                        "This route requires permission",
                        null,
                        ResponseType.ERROR.toString()
                );

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                String json = objectMapper.writeValueAsString(responseHeader.convertToMap());
                System.out.println(json);
                response.getWriter().write(json);
                return;
//                throw new AccessDeniedException("This route need authorization");
            }
        }
        final String token = authHeader.substring(7);
        System.out.println("Token: " + token);
        request.setAttribute("token", token);
        filterChain.doFilter(request, response);
    }


}
