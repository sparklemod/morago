<<<<<<<< HEAD:src/main/java/com/example/morago/controller/dto/auth/AuthResponse.java
package com.example.morago.controller.dto.auth;
========
package com.example.morago.controller.dto.response.auth;
>>>>>>>> dev:src/main/java/com/example/morago/controller/dto/response/auth/AuthResponse.java

import lombok.Data;

@Data
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }
}
