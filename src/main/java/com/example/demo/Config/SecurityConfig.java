package com.example.demo.Config;

import com.example.demo.Auth.CustomAccessDeniedHandler;
import com.example.demo.Auth.RequestResponseLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Checked security");
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated())

                .addFilterBefore(new RequestResponseLoggingFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        System.out.println("Check custom route");
        return web -> web.ignoring().requestMatchers("/products/**", "/categories/**",
                "/users/create",
                "/auth/login",
                "/sales/**",
                "/swagger/**", "/swagger-ui-custom.html",
                "/swagger-ui/**");
    }
}