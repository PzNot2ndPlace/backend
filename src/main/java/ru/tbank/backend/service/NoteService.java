package ru.tbank.backend.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.tbank.backend.dto.GptResponse;
import ru.tbank.backend.dto.NoteDto;
import ru.tbank.backend.dto.NoteDtoWithTriggers;
import ru.tbank.backend.dto.NoteTextDto;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    NoteDtoWithTriggers processText(NoteTextDto noteText);

    List<NoteDtoWithTriggers> getNotesByUserId(UUID userId);

    NoteDtoWithTriggers handleNote(@RequestBody GptResponse response, UUID userId);

}
