package com.example.morago.service;

import com.example.morago.model.dto.requests.call.CallCreateRequest;
import com.example.morago.model.dto.requests.call.CallPayload;
import com.example.morago.model.dto.response.calls.CallsGetHistoryResponse;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.CallStatusEnum;
import com.example.morago.model.entity.Call;
import com.example.morago.model.entity.Theme;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.model.enums.RoleEnum;
import com.example.morago.repository.CallRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallService {

    private final SimpMessagingTemplate messagingTemplate;
    private final CallRepository repository;
    private final UserProfileRepository userProfileRepository;
    private final UserService userService;
    private final ThemeService themeService;
    private final TranslatorRepository translatorRepository;

    public Page<CallsGetHistoryResponse> getCallHistory(Long userId, Pageable pageable) {
        User user = userService.getUserById(userId);

        if (user.getRolesAsEnumSet().contains(RoleEnum.ROLE_USER)) {
            return repository.findByCallerId(userId, pageable)
                .map(c -> CallsGetHistoryResponse.mapToDto(c, c.getCaller()));
        } else if (user.getRolesAsEnumSet().contains(RoleEnum.ROLE_TRANSLATOR)) {
            return repository.findByRecipientId(userId, pageable)
                .map(c -> CallsGetHistoryResponse.mapToDto(c, c.getRecipient()));
        }

        return Page.empty();
    }

    public Call createCall(CallCreateRequest request) {
        UserProfile caller = userProfileRepository.findById(request.getCallerId()).orElseThrow(()->new EntityNotFoundException("Caller not found"));
        Translator recipient = translatorRepository.findById(request.getRecipientId()).orElseThrow(()->new EntityNotFoundException("Translator not found"));
        Theme theme = themeService.getThemeOrThrow(request.getThemeId());

        if (caller.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new HandledException("Caller balance is negative");
        }

        Call call = Call.builder()
            .createdTime(LocalDateTime.now())
            .isEndCall(false)
            .status(false)
            .callStatus(CallStatusEnum.CONNECT_NOT_SET)
            .caller(caller)
            .recipient(recipient)
            .theme(theme)
            .build();

        Call saved = repository.save(call);

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
        Call call = repository.findById(callId)
            .orElseThrow(() -> new RuntimeException("Call not found"));
        LocalDateTime endTime = LocalDateTime.now();

        call.setIsEndCall(true);
        call.setStatus(true);
        call.setCallStatus(CallStatusEnum.COMPLETED);
        call.setDuration((int) Duration.between(call.getCreatedTime(), endTime).getSeconds());

        applyCallPayment(call);

        return repository.save(call);
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
        return repository.findById(id);
    }

    public void deleteCall(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Call not found");
        }
        repository.deleteById(id);
    }

    public Call acceptCall(Long id) {
        Call existingCall = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Call not found"));

        existingCall.setCallStatus(CallStatusEnum.STARTED);

        return repository.save(existingCall);
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

    public Call rateCall(Long id) {
        return new Call();
    }
}