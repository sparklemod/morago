package com.example.morago.model.entity;

import com.example.morago.model.dto.CallStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "calls")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caller_id")
    private UserProfile caller;
    private Boolean isEndCall;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Translator recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    private String channelName;

    @Enumerated(EnumType.STRING)
    private CallStatusEnum callStatus;
}
