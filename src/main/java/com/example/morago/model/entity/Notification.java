package com.example.morago.model.entity;

import com.example.morago.model.entity.base.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;
    private LocalDate date;
    private LocalTime time;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
