package com.example.morago.util.exception;

public class UserAlreadyExistsException extends HandledException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
