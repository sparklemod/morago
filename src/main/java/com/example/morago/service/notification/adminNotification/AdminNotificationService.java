package com.example.morago.service.notification.adminNotification;

import com.example.morago.model.entity.base.User;

import java.math.BigDecimal;

public interface AdminNotificationService {
    // Уведомление о новом пользователе
    void notifyRegistration(User user);
    // Уведомление о пополнение счета
    void notifyDeposit(User user, BigDecimal amount);
    // Уведомление о запросе на вывод средств
    void notifyWithdrawal(User user, BigDecimal amount);
}
