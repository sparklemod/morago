package com.example.morago.model.dto.response.user;

import com.example.morago.model.entity.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Фильтры и параметры пагинации для получения переводчиков")
public class UserGetResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private BigDecimal balance;

    public static UserGetResponse mapToDto(UserProfile userProfile) {
        return new UserGetResponse(
            userProfile.getId(),
            userProfile.getFirstName(),
            userProfile.getLastName(),
            userProfile.getPhone(),
            userProfile.getEmail(),
            userProfile.getBalance()
        );
    }
}