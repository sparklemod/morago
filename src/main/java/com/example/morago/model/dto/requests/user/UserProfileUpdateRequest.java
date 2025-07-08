package com.example.morago.model.dto.requests.user;

import com.example.morago.model.dto.requests.UserUpdateRequest;
import com.example.morago.model.entity.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User update request")
public class UserProfileUpdateRequest extends UserUpdateRequest {

    @Schema(description = "Coins", example = "1000")
    private BigDecimal coins;

    public UserProfile build(UserProfile userProfile)
    {
        return userProfile.toBuilder()
            .firstName(firstName)
            .lastName(lastName)
            .balance(coins)
            .build();
    }
}