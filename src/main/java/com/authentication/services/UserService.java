package com.authentication.services;

import com.authentication.models.User;
import com.authentication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private void verifyExistsUserByEmail(String email) throws InstanceAlreadyExistsException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new InstanceAlreadyExistsException("'email' exists");
        }
    }

    public User create(User user) throws InstanceAlreadyExistsException {
        verifyExistsUserByEmail(user.getEmail());
        return userRepository.save(user);
    }

}
