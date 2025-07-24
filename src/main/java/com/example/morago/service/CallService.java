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
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallService {

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

    public boolean isNotRecipient(Long userId, Long callId) {
        Call call = getById(callId);
        return !call.getRecipient().getId().equals(userId);
    }

    public CallPayload createCall(Long userId, CallCreateRequest request) {
        UserProfile caller = (UserProfile) userService.getUserById(userId);
        Translator translator = (Translator) userService.getUserById(request.getRecipientId());
        Theme theme = themeService.getThemeOrThrow(request.getThemeId());

        if (caller.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new HandledException("Caller balance is negative");
        }

        Call call = Call.builder()
            .isEndCall(false)
            .isFirst(repository.isFirstCall(translator.getId(), caller.getId()))
            .caller(caller)
            .recipient(translator)
            .theme(theme)
            .status(CallStatusEnum.INCOMING)
            .createdTime(request.getStartTime())
            .build();

        if (repository.hasActiveCall(translator.getId())) {
            call.setIsEndCall(true);
            call.setStatus(CallStatusEnum.BUSY);
            repository.save(call);

            throw new HandledException("Recipient is busy");
        }

        repository.save(call);

        log.info("Call created: caller={}, translator={}, status={}",
            caller.getId(),
            translator.getId(),
            call.getStatus()
        );

        return CallPayload.build(call);
    }

    public CallPayload acceptCall(Long id) {
        Call call = getById(id);
        call.setStatus(CallStatusEnum.STARTED);
        call.setStartTime(LocalDateTime.now());
        repository.save(call);

        log.info("Call accepted: caller={}, translator={}, status={}",
            call.getCaller().getId(),
            call.getRecipient().getId(),
            call.getStatus()
        );

        return CallPayload.build(call);
    }

    public CallPayload rejectCall(Long callId) {
        Call call = getById(callId);
        call.setStatus(CallStatusEnum.REJECTED);
        call.setEndTime(LocalDateTime.now());
        call.setIsEndCall(true);
        repository.save(call);

        log.info("Call rejected: caller={}, translator={}, status={}",
            call.getCaller().getId(),
            call.getRecipient().getId(),
            call.getStatus()
        );

        return CallPayload.build(call);
    }

    @Transactional
    public CallPayload endCall(Long callId) {
        Call call = getById(callId);

        if (call.getStatus() == CallStatusEnum.COMPLETED) {
            throw new HandledException("Call already ended");
        }

        call.setStatus(CallStatusEnum.COMPLETED);
        call.setIsEndCall(true);
        call.setEndTime(LocalDateTime.now());

        Integer duration = (int) Duration.between(call.getStartTime(), call.getEndTime()).getSeconds();
        call.setDuration(duration);

        try {
            applyCallPayment(call);
        } catch (RuntimeException e) {
            log.error("Payment processing failed for call {}: {}", callId, e.getMessage());
            call.setPaymentError(e.getMessage());
        }

        repository.save(call);

        log.info("Call is ended: caller={}, translator={}, status={}, paymentError={}",
            call.getCaller().getId(),
            call.getRecipient().getId(),
            call.getStatus(),
            call.getPaymentError()
        );

        if (call.getPaymentError() != null) {
            throw new HandledException("Payment processing failed");
        }

        return CallPayload.build(call);
    }

    public Call getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Call " + id + " not found"));
    }

    //TODO сделать
    public Call rateCall(Long id) {

        return new Call();
    }

    private void applyCallPayment(Call call) {
        BigDecimal totalPrice = call.getTotalPrice();

        BigDecimal commission = totalPrice.multiply(BigDecimal.valueOf(0.1));
        BigDecimal toTranslator = totalPrice.subtract(commission);

        UserProfile caller = call.getCaller();
        Translator recipient = call.getRecipient();

        caller.setBalance(caller.getBalance().subtract(totalPrice));
        recipient.setBalance(recipient.getBalance().add(toTranslator));

        userProfileRepository.save(caller);
        translatorRepository.save(recipient);

        call.setSum(totalPrice);
        call.setCommission(commission);
    }
}