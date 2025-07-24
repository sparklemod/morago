package com.example.morago.model.dto.response.user;

import com.example.morago.model.entity.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Фильтры и параметры пагинации для получения переводчиков")
public class UserGetResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private BigDecimal balance;
    private Boolean hasDepositRequest;

    public static UserGetResponse mapToDto(UserProfile u) {
        return UserGetResponse.builder()
            .id(u.getId())
            .firstName(u.getFirstName())
            .lastName(u.getLastName())
            .phone(u.getPhone())
            .balance(u.getBalance())
            .hasDepositRequest(u.hasActiveDeposit())
            .build();
    }
}