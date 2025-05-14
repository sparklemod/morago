package com.example.morago.entity.impl;

import com.example.morago.entity.base.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("TRANSLATOR")
@NoArgsConstructor
@AllArgsConstructor
public class Translator extends User {
    private LocalDate dateOfBirth;
    private boolean isOnline;
    private int levelOfKorean;
    private String memo;


}
