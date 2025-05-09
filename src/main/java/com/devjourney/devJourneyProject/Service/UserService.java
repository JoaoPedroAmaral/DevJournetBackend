package com.devjourney.devJourneyProject.Service;

import com.devjourney.devJourneyProject.Model.Users;
import com.devjourney.devJourneyProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepository passwordEncoder;

    public Users registerUser(Users user) {

        user.setRole("ROLE_USER"); // Define o papel padrão como "USER"
        return userRepository.save(user);
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
