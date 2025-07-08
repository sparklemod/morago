package com.example.morago.service;

import com.example.morago.model.dto.requests.call.CallCreateRequest;
import com.example.morago.model.dto.requests.call.CallPayload;
import com.example.morago.model.enums.CallStatusEnum;
import com.example.morago.model.entity.Call;
import com.example.morago.model.entity.Theme;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.repository.CallRepository;
import com.example.morago.repository.ThemeRepository;
import com.example.morago.repository.TranslatorRepository;
import com.example.morago.repository.UserProfileRepository;
import com.example.morago.util.exception.HandledException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallService {

    private final CallRepository callRepository;
    private final UserProfileRepository userProfileRepository;
    private final TranslatorRepository translatorRepository;
    private final ThemeRepository themeRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public Call createCall(CallCreateRequest request) {
        UserProfile caller = userProfileRepository.findById(request.getCallerId()).orElseThrow(()->new EntityNotFoundException("Caller not found"));
        Translator recipient = translatorRepository.findById(request.getRecipientId()).orElseThrow(()->new EntityNotFoundException("Translator not found"));
        Theme theme = themeRepository.findById(request.getThemeId()).orElseThrow(()->new EntityNotFoundException("Theme not found"));

        if (caller.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new HandledException("Caller balance is negative");
        }

        Call call = Call.builder()
            .createdAt(LocalDateTime.now())
            .isEndCall(false)
            .status(false)
            .channelName(request.getChannelName())
            .callStatus(CallStatusEnum.CONNECT_NOT_SET)
            .caller(caller)
            .recipient(recipient)
            .theme(theme)
            .build();

        Call saved = callRepository.save(call);

        CallPayload payload = new CallPayload(
            caller.getId().toString(),
            recipient.getId().toString(),
            request.getChannelName()
        );

        messagingTemplate.convertAndSendToUser(
            payload.getTo(),
            "/topic/incoming-call",
            payload
        );

        return saved;
    }

    public Call endCall(Long callId) {
        Call call = callRepository.findById(callId)
            .orElseThrow(() -> new RuntimeException("Call not found"));
        LocalDateTime endTime = LocalDateTime.now();

        call.setIsEndCall(true);
        call.setStatus(true);
        call.setCallStatus(CallStatusEnum.COMPLETED);
        call.setDuration((int) Duration.between(call.getCreatedAt(), endTime).getSeconds());

        applyCallPayment(call);

        return callRepository.save(call);
    }

    @Transactional
    public void applyCallPayment(Call call)
    {
        BigDecimal totalPrice = getTotalPrice(call);

        BigDecimal commission = totalPrice.multiply(BigDecimal.valueOf(0.1));
        BigDecimal toTranslator = totalPrice.subtract(commission);

        UserProfile caller = call.getCaller();
        Translator recipient = call.getRecipient();

        caller.setBalance(caller.getBalance().subtract(totalPrice));
        recipient.setBalance(recipient.getBalance().subtract(toTranslator));

        userProfileRepository.save(caller);
        translatorRepository.save(recipient);

        call.setSum(totalPrice);
        call.setCommission(commission);
    }

    public Optional<Call> getCall(Long id) {
        return callRepository.findById(id);
    }

    public void deleteCall(Long id) {
        if (!callRepository.existsById(id)) {
            throw new EntityNotFoundException("Call not found");
        }
        callRepository.deleteById(id);
    }

    public Call updateCallStatus(Long id, CallStatusEnum status) {
        Call existingCall = callRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Call not found"));

        existingCall.setCallStatus(status);

        return callRepository.save(existingCall);
    }

    private static BigDecimal getTotalPrice(Call call) {
        BigDecimal pricePerMinute = call.getTheme().getPrice();

        LocalTime now = LocalTime.now();
        LocalTime nightStart = LocalTime.of(22, 0);
        LocalTime nightEnd = LocalTime.of(6, 0);

        if (now.isAfter(nightStart) || now.isBefore(nightEnd))
        {
            pricePerMinute = call.getTheme().getNightPrice();
        }

        return pricePerMinute
            .multiply(BigDecimal.valueOf(call.getDuration()))
            .divide(BigDecimal.valueOf(60), RoundingMode.CEILING);
    }
}