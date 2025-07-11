package com.example.morago.model.entity;

import com.example.morago.model.enums.RoleEnum;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.*;

@Entity
@Getter
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleEnum name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}
