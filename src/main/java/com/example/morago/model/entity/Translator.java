package com.example.morago.model.entity;

import com.example.morago.model.entity.base.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("TRANSLATOR")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Translator extends User {

    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dateOfBirth;

    private boolean isOnline;

    @Min(value = 1, message = "Level of Korean must be at least 1")
    @Max(value = 5, message = "Level of Korean cannot exceed 5")
    private int levelOfKorean;

    @Size(max = 500, message = "Memo must be less than 500 characters")
    private String memo;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "translators")
    private Set<Theme> themes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "languageTranslators")
    private Set<Language> languages = new HashSet<>();

    @OneToMany(mappedBy = "recipient")
    private List<Call> calls;
}
