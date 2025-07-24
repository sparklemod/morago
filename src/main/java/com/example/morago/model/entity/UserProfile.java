package com.example.morago.model.entity;

import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile extends User {
    @Column(nullable = false)
    private Boolean isDebtor = false;

    @Column(nullable = false)
    private Boolean isFreeCallMade = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favorite_themes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private Set<Theme> favoriteThemes = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Deposit> deposits;

    public void addFavoriteTheme(Theme theme) {
        favoriteThemes.add(theme);
    }
    public void removeFavoriteTheme(Theme theme) {
        favoriteThemes.remove(theme);
    }

    public boolean hasActiveDeposit() {
        return !deposits.isEmpty() &&
            deposits.stream().anyMatch(d -> d.getStatus() == PaymentStatusEnum.PENDING);
    }
}
