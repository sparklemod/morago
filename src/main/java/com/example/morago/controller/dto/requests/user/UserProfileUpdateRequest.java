package com.example.morago.controller.dto.requests.user;

import com.example.morago.controller.dto.requests.UserUpdateRequest;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User update request")
public class UserProfileUpdateRequest extends UserUpdateRequest {

    @Schema(description = "Coins", example = "1000")
    private Long coins;

    public UserProfile build(File image)
    {
        return UserProfile
            .builder()
            .firstName(firstName)
            .lastName(lastName)
            .balance(coins)
            .imageFile(image)
            .build();
    }
}