package com.example.morago.controller.dto.requests.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User update balance request")
public class UserProfileUpdateBalanceRequest {

    @Schema(description = "User firstname", example = "Svetlana")
    private String firstName;

    @Schema(description = "User lastname", example = "Prokhorova")
    private String lastName;

    @Schema(description = "Phone number", example = "+82000000008")
    private String phone;

    @Schema(description = "Coins", example = "1000")
    private Long coins;
}