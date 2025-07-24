package com.example.morago.model.entity;

import com.example.morago.model.enums.CallStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    private LocalDateTime createdTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;
    private Boolean isEndCall;
    //является ли первым
    private Boolean status;
    private BigDecimal sum;
    private BigDecimal commission;
    private Boolean translatorHasRated;
    private Boolean userHasRated;

    @Min(1)
    @Max(5)
    private Byte userRating;

    @Min(1)
    @Max(5)
    private Byte translatorRating;

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
