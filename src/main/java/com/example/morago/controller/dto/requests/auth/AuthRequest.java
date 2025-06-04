<<<<<<<< HEAD:src/main/java/com/example/morago/controller/dto/auth/AuthRequest.java
package com.example.morago.controller.dto.auth;
========
package com.example.morago.controller.dto.requests.auth;
>>>>>>>> dev:src/main/java/com/example/morago/controller/dto/requests/auth/AuthRequest.java

import lombok.Data;

@Data
public class AuthRequest {
    private String phone;
    private String password;
}
