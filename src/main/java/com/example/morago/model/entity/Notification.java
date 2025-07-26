package com.example.morago.model.entity;

import com.example.morago.model.entity.base.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

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
    private LocalDateTime date;
    private Boolean isRead;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
