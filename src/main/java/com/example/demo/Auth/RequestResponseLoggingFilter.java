package com.example.demo.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.ResponseType;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;


public class RequestResponseLoggingFilter implements Filter {


    public void writeResponse(ResponseHeader responseHeader, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(responseHeader.convertToMap());
        System.out.println(json);
        response.getWriter().write(json);
    }

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
            return;
        }
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

            writeResponse(responseHeader, response);
            return;
        }

        final String jwtToken = authHeader.substring(7);

        try {
            DecodedJWT decodedJWT = JWT.decode(jwtToken);


            if (!decodedJWT.getExpiresAt().before(new Date())) throw new JWTDecodeException("Token is expired");
            String result = decodedJWT.getClaim("userId").asString();
            if (result == null) throw new JWTDecodeException("Token is error");

            GrantedAuthority authority = new SimpleGrantedAuthority("user");
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(result, jwtToken, Arrays.asList(authority));
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (JWTDecodeException jwtDecodeException) {
            response.setStatus(403);
            response.setContentType("application/json");
            // Create a JSON response object
            ResponseHeader responseHeader = new ResponseHeader(
                    LocalDateTime.now(),
                    "PERMISSION_ERROR",
                    jwtDecodeException.getMessage(),
                    null,
                    ResponseType.ERROR.toString()
            );
            writeResponse(responseHeader, response);
        }

    }
}
