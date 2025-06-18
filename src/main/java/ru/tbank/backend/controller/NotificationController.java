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
import ru.tbank.backend.service.impl.HintNoteSchedulerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@Tag(name = "Уведомления", description = "Контроллер, отвечающий за уведомления")
public class NotificationController {

    private final NotificationService notificationService;
    private final HintNoteSchedulerService hintNoteSchedulerService;

    @PostMapping("/test")
    private void test(
            @RequestParam String message
    ) {
        notificationService.testNotification(message);
    }

    @PostMapping("/test/hints")
    private void testHints() {
        hintNoteSchedulerService.testHints();
    }

    @PostMapping("/token/save")
    private void saveToken(
            @RequestParam String token,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        notificationService.saveToken(token, customUserDetails.getId());
    }

}
