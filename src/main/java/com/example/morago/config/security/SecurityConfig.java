package com.example.morago.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //    TODO спросить про доступы по ролям
//    временно открытый доступ для тестирования api
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/**").permitAll()
 //                               // Публичные эндпоинты
//                        .requestMatchers("/auth/login", "/auth/register/user", "/auth/register/translator", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                                .requestMatchers("/api/themes", "/api/themes/{id}").permitAll()
//                        // Админские эндпоинты
//                        .requestMatchers("/admin/**", "/api/admin/themes/**).hasRole("ADMIN")
  //                              .requestMatchers("/files/upload").authenticated()
 //                               // Пользовательские эндпоинты
    //                            .requestMatchers("/user/profile/image").hasAnyRole("USER", "TRANSLATOR")
//                        .requestMatchers("/translator/**").hasRole("TRANSLATOR")
//                        .requestMatchers("/user/**").hasRole("USER")
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
