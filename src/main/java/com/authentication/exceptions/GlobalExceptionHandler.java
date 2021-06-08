package com.authentication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Message> response(String message, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(Message.builder()
                        .message(message)
                        .build());
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Message> methodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        return response(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NoSuchElementException.class })
    public ResponseEntity<Message> noSuchElement(NoSuchElementException e) {
        String message = e.getMessage();
        return response(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { InstanceAlreadyExistsException.class })
    public ResponseEntity<Message> InstanceAlreadyExists(InstanceAlreadyExistsException e) {
        String message = e.getMessage();
        return response(message, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(value = { InternalAuthenticationServiceException.class })
    public ResponseEntity<Message> InternalAuthenticationService(InternalAuthenticationServiceException e) {
        String message = e.getMessage();
        return response(message, HttpStatus.BAD_REQUEST);
    }

}
