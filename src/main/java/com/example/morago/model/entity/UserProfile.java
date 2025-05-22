package com.example.morago.model.entity;

import com.example.morago.model.entity.base.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("USER")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile extends User {
    @Column(name = "is_debtor", nullable = false)
    private Boolean isDebtor = false;

    @Column(name = "is_free_call_made", nullable = false)
    private Boolean isFreeCallMade = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favorite_themes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private Set<Theme> favoriteThemes = new HashSet<>();

    @OneToMany(mappedBy = "caller")
    private Set<Call> calls;

    public void addFavoriteTheme(Theme theme) {
        favoriteThemes.add(theme);
    }
    public void removeFavoriteTheme(Theme theme) {
        favoriteThemes.remove(theme);
    }
}
