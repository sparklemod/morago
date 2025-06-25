package com.example.morago.util.exception;

import lombok.Getter;

@Getter
public class HandledException extends RuntimeException {
    public HandledException(String message) {
        super(message);
    }
}
