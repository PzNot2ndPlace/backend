package ru.tbank.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class FirebaseConfig {

    @Value("${firebase.credentials}")
    private String firebaseConfig;

    @PostConstruct
    public void init() {
        if (firebaseConfig == null) {
            log.warn("Firebase config not found. Firebase features will be disabled.");
            return;
        }

        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ByteArrayInputStream(firebaseConfig.getBytes())))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase initialized successfully");
            }
        } catch (IOException e) {
            log.error("Failed to initialize Firebase", e);
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        if (firebaseConfig == null) {
            log.warn("FirebaseMessaging bean is not available - no config provided");
            return null;
        }

        try {
            return FirebaseMessaging.getInstance();
        } catch (IllegalStateException e) {
            log.error("Failed to create FirebaseMessaging bean", e);
            return null;
        }
    }
}