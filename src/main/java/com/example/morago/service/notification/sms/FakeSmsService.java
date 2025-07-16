package com.example.morago.service.notification.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FakeSmsService implements SmsService {
    @Override
    public void send(String phone, String message) {
        log.info("FAKE‑SMS → {} :: {}", phone, message);
        // в проде заменить на реальный смс сервис.
    }
}
