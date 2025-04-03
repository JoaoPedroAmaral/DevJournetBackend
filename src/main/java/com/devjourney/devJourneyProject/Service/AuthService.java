package com.devjourney.devJourneyProject.Service;

import com.devjourney.devJourneyProject.Model.Users;
import com.devjourney.devJourneyProject.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Chave secreta para assinar o token JWT (substitua por uma chave segura)
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode("bXktc2VjcmV0LWtleS1mb3ItanNvbi13ZWItdG9rZW4="));

    // Tempo de expiração do token (24 horas)
    private final long EXPIRATION_TIME = 86400000; // 24 horas em milissegundos

    public String authenticate(String username, String password) {
        // Busca o usuário no banco de dados
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verifica se a senha está correta
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Gera o token JWT
        return Jwts.builder()
                .claim("sub", user.getUsername()) // Define o "subject" como o nome de usuário
                .setIssuedAt(new Date()) // Define a data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Define a data de expiração
                .signWith(SECRET_KEY) // Assina o token com a chave secreta
                .compact();
    }
}