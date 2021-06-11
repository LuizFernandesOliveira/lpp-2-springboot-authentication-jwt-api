package com.authentication.controllers;

import com.authentication.dtos.UserDTO;
import com.authentication.exceptions.Message;
import com.authentication.models.User;
import com.authentication.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid User user) throws InstanceAlreadyExistsException {
        UserDTO userCreated = UserDTO.mapToDTO(userService.create(user));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userCreated);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        UserDTO user = UserDTO.mapToDTO(userService.getUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        UserDTO userUpdated = UserDTO.mapToDTO(userService.updateUser(user));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUpdated);
    }

    @DeleteMapping
    public ResponseEntity<Message> deleteUser() {
        Message message = userService.deleteUser();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(message);
    }

}
