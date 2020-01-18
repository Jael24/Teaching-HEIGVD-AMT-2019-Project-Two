package ch.heigvd.amt.project.two.auth.services;

import ch.heigvd.amt.project.two.auth.entities.UserEntity;
import ch.heigvd.amt.project.two.auth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }
}