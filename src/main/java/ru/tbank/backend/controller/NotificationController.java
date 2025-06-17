package ru.tbank.backend.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tbank.backend.config.userDetails.CustomUserDetails;
import ru.tbank.backend.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@Tag(name = "Уведомления", description = "Контроллер, отвечающий за уведомления")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/test")
    private void test(
            @RequestParam String message
    ) {
        notificationService.testNotification(message);
    }

    @PostMapping("/token/save")
    private void saveToken(
            @RequestParam String token,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        notificationService.saveToken(token, customUserDetails.getId());
    }

}
