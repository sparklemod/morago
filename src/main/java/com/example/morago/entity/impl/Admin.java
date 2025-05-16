package com.example.morago.entity.impl;

import com.example.morago.entity.base.User;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("ADMIN")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User {

    @Column(name = "is_super_admin", nullable = false)
    private boolean isSuperAdmin = false;

}
