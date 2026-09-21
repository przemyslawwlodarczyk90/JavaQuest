package com.example.javaquest.platform.auth;

import com.example.javaquest.platform.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Cale REST API platformy ({@code /api/**}) wymaga zalogowania - wyjatek to trzy publiczne endpointy
 * {@code /api/auth/*} (rejestracja, potwierdzenie konta, logowanie). Reszta (statyczny frontend React,
 * fallback SPA) jest otwarta, bo to sam kod aplikacji - tresc lekcji leci wylacznie przez /api.
 * Bramke widoczna dla uzytkownika (ekran logowania) robi frontend, ale to TEN plik jest realnym
 * zabezpieczeniem danych.
 *
 * <p>Wymaga, zeby globalne wykluczenia auto-konfiguracji Spring Security (application.properties)
 * zostaly zdjete dla platformy - patrz {@code JavaQuestApplication}.
 */
@Configuration
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService,
                                            UserRepository userRepository) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // brak sesji/ciasteczek - token w naglowku, CSRF nie dotyczy
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login", "/api/auth/confirm").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(new JwtAuthFilter(jwtService, userRepository),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
