package ru.tbank.backend.service.impl;

import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.tbank.backend.entity.UserEntity;
import ru.tbank.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    @Transactional
    public void sendEmail(UUID userId, String subject, String body) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException("")
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("den700l@yandex.ru");
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
