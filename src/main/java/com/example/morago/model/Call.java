package com.example.morago.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private Integer duration;
    private Boolean status;
    private BigDecimal sum;
    private BigDecimal commission;
    private Boolean translatorHasRated;
    private Boolean userHasRated;
    private LocalDateTime updatedAt;
    private Long callerId;
    private Long recipientId;
    private Long themeId;
    private String channelName;
    private Integer callStatus;
    private Boolean isEndCall;
}
