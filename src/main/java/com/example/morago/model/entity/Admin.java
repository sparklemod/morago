package com.example.morago.model.entity;
import com.example.morago.model.entity.base.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User {

    @Column(nullable = false)
    private Boolean isSuperAdmin = false;

}
