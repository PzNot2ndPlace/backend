package ru.tbank.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.tbank.backend.config.userDetails.CustomUserDetails;
import ru.tbank.backend.dto.GptResponse;
import ru.tbank.backend.dto.NoteDto;
import ru.tbank.backend.dto.NoteDtoWithTriggers;
import ru.tbank.backend.dto.NoteTextDto;
import ru.tbank.backend.enums.CategoryType;
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
    @GetMapping("/my")
    private List<NoteDtoWithTriggers> getMyNotes(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        //TODO фильтры пагинации
        return noteService.getNotesByUserId(customUserDetails.getId());
    }

    @Operation(
            summary = "Отправить текст заметки на обработку",
            description = "Позволяет пользователю отправить текст заметки на обработку и получить готовую заметку"
    )
    @PostMapping("/handle")
    private NoteDtoWithTriggers handleNote(
            @RequestBody GptResponse response,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return noteService.handleNote(response, customUserDetails.getId());
    }

    @Operation(
            summary = "Отправить текст заметки на обработку",
            description = "Позволяет пользователю отправить текст заметки на обработку и получить готовую заметку"
    )
    @PutMapping("/{noteId}/update")
    private NoteDtoWithTriggers updateNote(
            @PathVariable("noteId") UUID noteId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) CategoryType categoryType,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) UUID triggerId,
            @RequestParam(required = false) String triggerValue
    ) {
        return noteService.updateNote(noteId, customUserDetails.getId(), categoryType, text, triggerId, triggerValue);
    }

}