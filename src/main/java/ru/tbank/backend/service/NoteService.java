package ru.tbank.backend.service;

import ru.tbank.backend.dto.NoteDto;
import ru.tbank.backend.dto.NoteTextDto;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    NoteDto processText(NoteTextDto noteText);

    List<NoteDto> getNotesByUserId(UUID userId);

}
