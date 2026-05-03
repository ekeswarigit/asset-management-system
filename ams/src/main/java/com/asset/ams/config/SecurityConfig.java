package com.asset.ams.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CorsConfigurationSource corsConfigurationSource;
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth

            //  allow swagger
            .requestMatchers("/v3/api-docs/**",
                             "/swagger-ui/**",
                             "/swagger-ui.html").permitAll()
            // Auth
            .requestMatchers("/api/auth/login",
                             "/api/auth/register").permitAll()
            // OPTIONS
            .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
            //  GET users → allow ADMIN + USER
            .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("ADMIN", "USER")
            //  CREATE user → ADMIN only
            .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
            // Admin routes
            .requestMatchers("/admin/**").hasRole("ADMIN")
            // Everything else
            .anyRequest().authenticated()
        );

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}