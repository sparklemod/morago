package com.example.morago.model.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private Integer phone;
    private String firstName;
    private String lastName;
}
