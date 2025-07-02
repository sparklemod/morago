package com.example.morago.model.dto.requests.user;

public record UpdatePasswordRequest(String oldPassword, String newPassword, String confirmPassword) { }
