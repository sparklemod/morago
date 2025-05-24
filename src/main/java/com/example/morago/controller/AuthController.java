package com.example.morago.controller;

import com.example.morago.model.dto.UserResponse;
import com.example.morago.model.entity.base.User;
import com.example.morago.controller.dto.AuthRequest;
import com.example.morago.controller.dto.AuthResponse;
import com.example.morago.service.UserService;
import com.example.morago.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            String.valueOf(authRequest.getPhone()),
                            authRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid phone or password.");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(authRequest.getPhone()));
        String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(createdUser.getId());
        userResponse.setPhone(createdUser.getPhone());
        userResponse.setFirstName(createdUser.getFirstName());
        userResponse.setLastName(createdUser.getLastName());
        return userResponse;
    }
}
