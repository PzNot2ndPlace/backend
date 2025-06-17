package ru.tbank.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials:#{null}}")
    private String firebaseConfig;

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        if (!StringUtils.hasText(firebaseConfig)) {
            throw new IllegalStateException("Firebase config is empty. Set 'firebase.credentials' property");
        }

        try {
            new Gson().fromJson(firebaseConfig, Object.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Firebase JSON configuration", e);
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(
                        new ByteArrayInputStream(firebaseConfig.getBytes())))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        log.info("Firebase initialized successfully for project: {}", options.getProjectId());
        return FirebaseMessaging.getInstance(app);
    }
}
