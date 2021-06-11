package com.authentication.services;

import com.authentication.models.User;
import com.authentication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoggedUser loggedUser;

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

    public User getUser() {
        User user = loggedUser.get();
        if (user != null) {
            return user;
        } else {
            throw new NoSuchElementException("user not found");
        }
    }
}
