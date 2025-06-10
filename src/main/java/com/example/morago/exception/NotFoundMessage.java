package com.example.morago.exception;

public enum NotFoundMessage {
    USER("User"),
    IMAGE("Image"),
    THEME("Theme");

    private final String entity;

    NotFoundMessage(String entity) {
        this.entity = entity;
    }

    public String format() {
        return entity + " not found";
    }
}
