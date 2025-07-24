package com.example.morago.model.entity.base;

import ch.qos.logback.core.util.StringUtil;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.Role;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    protected Set<Role> roles = new HashSet<>();

    @Column(nullable = false, unique = true)
    protected String phone;

    @Column(nullable = false)
    protected String password;

    protected String firstName;

    protected String lastName;

    @Column(unique = true)
    protected String email;

    @Column(nullable = false)
    protected Boolean isActive;

    @Column(nullable = false)
    protected BigDecimal balance;

    @OneToOne(fetch = FetchType.LAZY)
    protected File imageFile;

    public String getNameWithSurname(){
        return getFirstName() + " " + getLastName();
    }

    public String getNameWithInitials(){
        return getFirstName().substring(0,1).toUpperCase() + ". " + StringUtil.capitalizeFirstLetter(getLastName());
    }
}
