package com.authentication.controllers;

import com.authentication.dtos.UserDTO;
import com.authentication.models.User;
import com.authentication.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
