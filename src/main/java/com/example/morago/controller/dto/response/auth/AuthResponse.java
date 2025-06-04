<<<<<<<< HEAD:src/main/java/com/example/morago/controller/dto/auth/AuthResponse.java
package com.example.morago.controller.dto.auth;
========
package com.example.morago.controller.dto.response.auth;
>>>>>>>> dev:src/main/java/com/example/morago/controller/dto/response/auth/AuthResponse.java

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private Long id;
    private String phone;
    private String firstName;
    private String lastName;
}
