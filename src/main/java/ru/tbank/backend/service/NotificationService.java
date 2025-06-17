package ru.tbank.backend.service;

import java.util.UUID;

public interface NotificationService {

    void testNotification(String message);

    void saveToken(String token, UUID userId);

    void sendNotification(UUID userId, String title, String body);

}
