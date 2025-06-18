package com.example.morago.exception;

public class UserAlreadyExistsException extends HandledException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
