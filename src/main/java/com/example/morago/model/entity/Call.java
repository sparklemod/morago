package com.example.morago.model.entity;

import com.example.morago.model.enums.CallStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "calls")
@Getter
@Setter
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
    private Boolean isEndCall;
    private String channelName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caller_id")
    private UserProfile caller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Translator recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Enumerated(EnumType.STRING)
    private CallStatusEnum callStatus;
}
