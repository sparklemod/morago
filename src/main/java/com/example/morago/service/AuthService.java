package com.example.morago.service;

import com.example.morago.config.security.userDetails.CustomUserDetails;
import com.example.morago.config.security.JwtUtil;
import com.example.morago.model.dto.requests.auth.UserCreateRequest;
import com.example.morago.model.dto.response.auth.AuthResponse;
import com.example.morago.model.entity.Role;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.RoleEnum;
import com.example.morago.repository.RoleRepository;
import com.example.morago.repository.UserRepository;
import com.example.morago.util.exception.HandledException;
import com.example.morago.util.exception.enums.NotFoundMessage;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthResponse registerUser(UserCreateRequest req) {
        return register(req, RoleEnum.ROLE_USER);
    }

    public AuthResponse registerTranslator(UserCreateRequest req) {
        return register(req, RoleEnum.ROLE_TRANSLATOR);
    }

    private AuthResponse register(UserCreateRequest req, RoleEnum roleEnum) {
        userService.checkIsExistByPhone(req.getPhone());

        Role role = roleRepository.findByName(roleEnum)
            .orElseThrow(() -> new HandledException(NotFoundMessage.ROLE.format()));

        User user = createUser(req, role);
        userRepository.save(user);

        return auth(req.getPhone(), req.getPassword());
    }

    public AuthResponse auth(String phone, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(phone, password));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return buildAuthResponse(userDetails);
    }

    private AuthResponse buildAuthResponse(CustomUserDetails userDetails) {
        String token = jwtUtil.generateToken(userDetails);
        String roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        return AuthResponse.builder()
            .token(token)
            .id(userDetails.getId())
            .phone(userDetails.getUsername())
            .firstName(userDetails.getFirstName())
            .lastName(userDetails.getLastName())
            .roles(roles)
            .build();
    }

    private User createUser(UserCreateRequest req, Role role) {
        User user = new UserProfile();
        user.setIsActive(true);

        if (role.getName().equals(RoleEnum.ROLE_TRANSLATOR)) {
            user = new Translator();
            user.setIsActive(false);
        }

        user.setPhone(req.getPhone());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setBalance(BigDecimal.ZERO);
        user.setRoles(Set.of(role));

        return user;
    }
}
