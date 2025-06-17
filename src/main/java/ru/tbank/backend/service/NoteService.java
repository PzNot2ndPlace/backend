package ru.tbank.backend.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tbank.backend.dto.GptResponse;
import ru.tbank.backend.dto.NoteDto;
import ru.tbank.backend.dto.NoteDtoWithTriggers;
import ru.tbank.backend.dto.NoteTextDto;
import ru.tbank.backend.enums.CategoryType;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    NoteDtoWithTriggers processText(NoteTextDto noteText);

    List<NoteDtoWithTriggers> getNotesByUserId(UUID userId);

    NoteDtoWithTriggers handleNote(@RequestBody GptResponse response, UUID userId);

    NoteDtoWithTriggers updateNote(
            @PathVariable UUID noteId,
            UUID userId,
            CategoryType categoryType,
            String text,
            UUID triggerId,
            String triggerValue
    );

}
