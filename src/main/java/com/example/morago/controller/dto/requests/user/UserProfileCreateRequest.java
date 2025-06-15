package com.example.morago.controller.dto.requests.user;

import com.example.morago.model.entity.File;
import com.example.morago.model.entity.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User create request")
public class UserProfileCreateRequest {

    @Schema(description = "User firstname", example = "Svetlana")
    private String firstName;

    @Schema(description = "User lastname", example = "Prokhorova")
    private String lastName;

    @Schema(description = "User password", example = "password")
    private String password;

    @Schema(description = "Phone number", example = "+82000000008")
    private String phone;

    @Schema(description = "Coins", example = "1000")
    private Long coins;

    @Schema(description = "Image URL")
    private String imageUrl;

    public UserProfile build(File image)
    {
        return UserProfile
            .builder()
            .firstName(firstName)
            .lastName(lastName)
            .phone(phone)
            .balance(coins)
            .password(password)
            .imageFile(image)
            .isActive(true)
            .isDebtor(false)
            .isFreeCallMade(false)
            .build();
    }
}