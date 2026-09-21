package com.example.javaquest.platform.auth;

import java.io.IOException;

import com.example.javaquest.platform.user.User;
import com.example.javaquest.platform.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Czyta naglowek "Authorization: Bearer ...", weryfikuje token i - jesli konto istnieje i jest
 * aktywne - ustawia uwierzytelnienie. Zly/wygasly token NIE rzuca wyjatku, tylko zostawia zadanie
 * anonimowe (a reguly w {@link SecurityConfig} odpowiedza wtedy 401).
 *
 * <p>Celowo NIE jest beanem Springa (@Component): Spring Boot rejestruje kazdy bean-Filter takze
 * jako zwykly filtr serwletu, co uruchomiloby go dwa razy. Tworzy go i wpina {@link SecurityConfig}.
 */
class JwtAuthFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserRepository userRepository;

    JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith(BEARER_PREFIX)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            jwtService.extractEmail(header.substring(BEARER_PREFIX.length()))
                    .flatMap(userRepository::findByEmail)
                    .filter(User::isEnabled)
                    .ifPresent(user -> {
                        var authorities = user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.name()))
                                .toList();
                        SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities));
                    });
        }
        chain.doFilter(request, response);
    }
}
