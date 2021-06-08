package com.authentication.services;

import com.authentication.config.security.Web;
import com.authentication.models.User;
import com.authentication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        user.setPassword(Web.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

}
