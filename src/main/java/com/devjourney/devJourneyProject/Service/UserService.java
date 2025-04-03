package com.devjourney.devJourneyProject.Service;

import com.devjourney.devJourneyProject.Model.Users;
import com.devjourney.devJourneyProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para registrar um novo usuário
    public Users registerUser(Users user) {
        // Criptografa a senha antes de salvar no banco de dados
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER"); // Define o papel padrão como "USER"
        return userRepository.save(user);
    }

    // Método para buscar um usuário pelo nome de usuário
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Método para verificar se um usuário existe pelo nome de usuário
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
