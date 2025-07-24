package com.example.morago.service.notification.adminNotification;

import com.example.morago.model.entity.base.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@Profile("dev") // или "prod"
@RequiredArgsConstructor
public class MailTrapAdminNotificationService implements AdminNotificationService {

    private static final Logger log = LoggerFactory.getLogger(MailTrapAdminNotificationService.class);
    private final JavaMailSender mailSender;

    @Value("${admin.email}")  // email Админа.
    private String adminEmail;

    @Override
    public void notifyRegistration(User user) {
        String subject = "New Registration";
        String body = "User " + user.getId() + " has been registered.";
        send(subject, body);
    }

    @Override
    public void notifyDeposit(User user, BigDecimal amount) {
        String subject = "New Deposit";
        String body = "User " + user.getId() + " has been deposited: " + amount + ".";
        send(subject, body);
    }

    @Override
    public void notifyWithdrawal(User user, BigDecimal amount) {
        String subject = "New Withdrawal";
        String body = "User " + user.getId() + " withdrawal request: " + amount + ".";
        send(subject, body);
    }

    private void send(String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(adminEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            log.warn("Error while sending email to {}", adminEmail, e);
        }
    }
}
