package ru.tbank.backend.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tbank.backend.entity.FcmTokenEntity;
import ru.tbank.backend.repository.FcmTokenRepository;
import ru.tbank.backend.service.NotificationService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final FcmTokenRepository repository;
    private final FirebaseMessaging firebaseMessaging;
    private final EmailService emailService;

    @Override
    public void testNotification(String message) {
        var tokens = repository.findAll();

        tokens.forEach(it -> {
                    sendNotification(it.getToken(), "title", "купить пива в 24:00 в Подкове");
                }
        );
    }

    @Transactional
    @Override
    public void saveToken(String token, UUID userId) {
        repository.save(
                new FcmTokenEntity(token, userId)
        );
    }

    @Override
    public void sendNotification(UUID userId, String title, String body) {
        log.info("Начало отправки уведомления");
        var tokens = repository.findAllByUserId(userId);

        tokens.forEach(it -> {
                    sendNotification(it.getToken(), title, body);
                }
        );
        emailService.sendEmail(userId, title, body);
    }

    private void sendNotification(String token, String title, String body) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        try {
            firebaseMessaging.send(message);
            log.info("Отправлено сообщение токен: {}, тайтл: {}, сообщение: {}", token, title, body);
        } catch (FirebaseMessagingException e) {
            log.error("Не удалось отправить уведомление", e);
        }
    }

}
