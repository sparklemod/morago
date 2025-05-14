package com.example.morago.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Getter
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
    private Long userId;
}
