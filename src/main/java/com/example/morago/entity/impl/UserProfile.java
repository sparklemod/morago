package com.example.morago.entity.impl;

import com.example.morago.entity.base.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("USER")
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile extends User {
    private boolean isDebtor;
    private boolean isFreeCallMade;

}
