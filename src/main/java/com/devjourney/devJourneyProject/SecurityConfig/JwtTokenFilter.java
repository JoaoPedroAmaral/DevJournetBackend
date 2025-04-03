package com.devjourney.devJourneyProject.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.security.Key;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    // Chave secreta para validar o token JWT (deve ser a mesma usada no AuthService)
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode("bXktc2VjcmV0LWtleS1mb3ItanNvbi13ZWItdG9rZW4="));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null && validateToken(token)) {
            String username = extractUsername(token);
            System.out.println("Usuário autenticado: " + username); // Log para depuração

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, null);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            System.out.println("Token inválido ou ausente."); // Log para depuração
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " do início do token
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true; // Token válido
        } catch (Exception e) {
            System.out.println("Erro ao validar o token: " + e.getMessage()); // Log para depuração
            return false; // Token inválido ou expirado
        }
    }

    private String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("sub", String.class);
    }
}