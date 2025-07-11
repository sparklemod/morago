package com.example.morago.util.exception.enums;

public enum NotFoundMessage {
    USER("User"),
    IMAGE("Image"),
    THEME("Theme"),
    ROLE("Role");

    private final String entity;

    NotFoundMessage(String entity) {
        this.entity = entity;
    }

    public String format() {
        return entity + " not found";
    }
}
