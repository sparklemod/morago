package com.example.morago.service;

import com.example.morago.controller.dto.websocket.CallPayload;
import com.example.morago.enums.CallStatusEnum;
import com.example.morago.model.entity.Call;
import com.example.morago.model.entity.Theme;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.repository.CallRepository;
import com.example.morago.repository.ThemeRepository;
import com.example.morago.repository.TranslatorRepository;
import com.example.morago.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
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

    public Call createCall(Long callerId, Long recipientId, Long themeId, String channelName) {
        UserProfile caller = userProfileRepository.findById(callerId).orElseThrow(()->new EntityNotFoundException("Caller not found"));
        Translator recipient = translatorRepository.findById(recipientId).orElseThrow(()->new EntityNotFoundException("Translator not found"));
        Theme theme = themeRepository.findById(themeId).orElseThrow(()->new EntityNotFoundException("Theme not found"));

        Call call = Call.builder()
            .createdAt(LocalDateTime.now())
            .isEndCall(false)
            .status(false)
            .channelName(channelName)
            .callStatus(CallStatusEnum.CONNECT_NOT_SET)
            .caller(caller)
            .recipient(recipient)
            .theme(theme)
            .build();

        Call saved = callRepository.save(call);

        CallPayload payload = new CallPayload(
            caller.getId().toString(),
            recipient.getId().toString(),
            channelName
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
        call.setUpdatedAt(endTime);
        call.setIsEndCall(true);
        call.setStatus(true);
        call.setCallStatus(CallStatusEnum.COMPLETED);
        call.setDuration((int) Duration.between(call.getCreatedAt(), endTime).getSeconds());
        return callRepository.save(call);
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

    public Call updateCall(Long id, Call updatedCall) {
        Call existingCall = callRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Call not found"));

        existingCall.setUpdatedAt(LocalDateTime.now());
        existingCall.setStatus(updatedCall.getStatus());
        existingCall.setIsEndCall(updatedCall.getIsEndCall());
        existingCall.setCallStatus(updatedCall.getCallStatus());
        existingCall.setSum(updatedCall.getSum());
        existingCall.setCommission(updatedCall.getCommission());
        existingCall.setTranslatorHasRated(updatedCall.getTranslatorHasRated());
        existingCall.setUserHasRated(updatedCall.getUserHasRated());

        return callRepository.save(existingCall);
    }
}