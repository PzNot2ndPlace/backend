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
    private String firebaseConfigJson;

    @PostConstruct
    public void initialize() {
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(getCredentials())
                        .build();

                FirebaseApp.initializeApp(options);
                log.info("Firebase Admin SDK initialized successfully");

            } catch (IOException e) {
                log.error("Failed to initialize Firebase Admin SDK", e);
                throw new IllegalStateException("Firebase initialization failed", e);
            }
        }
    }

    private GoogleCredentials getCredentials() throws IOException {
        if (firebaseConfigJson == null || firebaseConfigJson.isBlank()) {
            throw new IllegalStateException("Firebase config not found in environment variables");
        }

        log.debug("Initializing Firebase from environment variable");
        try (InputStream is = new ByteArrayInputStream(
                firebaseConfigJson.getBytes(StandardCharsets.UTF_8))) {
            return GoogleCredentials.fromStream(is);
        }
    }
}