package com.example.morago.model.entity.base;

import com.example.morago.model.entity.File;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User extends Auditable implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer phone;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8,  message = "Password must be at least 8 characters")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "Can not be empty")
    @Size(max = 50, message = "Must be less than 50 characters")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Can not be empty")
    @Size(max = 50, message = "Must be less than 50 characters")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    @Min(0)
    private Long balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private File imageId;

    @Override
    public String getUsername() {
        return String.valueOf(this.phone);
    }

    @Override
    public String getPassword() {
        return String.valueOf(this.password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.getClass().getSimpleName().toUpperCase()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
