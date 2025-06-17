package ru.tbank.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tbank.backend.config.userDetails.CustomUserDetails;
import ru.tbank.backend.dto.NoteDto;
import ru.tbank.backend.dto.NoteDtoWithTriggers;
import ru.tbank.backend.dto.NoteTextDto;
import ru.tbank.backend.service.NoteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
@Tag(name = "Заметки", description = "Контроллер, отвечающий за заметки")
public class NoteController {

    private final NoteService noteService;

    @Operation(
            summary = "Отправить текст заметки на обработку",
            description = "Позволяет пользователю отправить текст заметки на обработку и получить готовую заметку"
    )
    @PostMapping("/process-text")
    private NoteDtoWithTriggers sendText(
            @RequestBody NoteTextDto noteText
    ) {
        return noteService.processText(noteText);
    }

    @Operation(
            summary = "Получить свои заметки",
            description = "Позволяет пользователю получить свои заметки"
    )
    @PostMapping("/my")
    private List<NoteDtoWithTriggers> getMyNotes(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        //TODO фильтры пагинации
        return noteService.getNotesByUserId(customUserDetails.getId());
    }

}