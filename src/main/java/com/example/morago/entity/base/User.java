package com.example.morago.entity.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int phone;
    @Column(nullable = false)
    private long password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column
    private long balance;

    @Column
    private long imageID;


}
