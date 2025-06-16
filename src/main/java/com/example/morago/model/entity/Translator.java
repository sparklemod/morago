package com.example.morago.model.entity;

import com.example.morago.model.entity.base.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("TRANSLATOR")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Translator extends User {

    private LocalDate dateOfBirth;

    private Boolean isOnline;

    private Integer levelOfKorean;

    @Size(max = 500, message = "Memo must be less than 500 characters")
    private String memo;

    @ManyToMany
    @JoinTable(
            name = "translator_theme",
            joinColumns = @JoinColumn(name = "translator_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private Set<Theme> themes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "translator_language",
            joinColumns = @JoinColumn(name = "translator_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private Set<Language> languages = new HashSet<>();

    @OneToMany(mappedBy = "recipient")
    private Set<Call> calls;
}
