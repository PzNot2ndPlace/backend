package ru.tbank.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tbank.backend.dto.NoteDto;
import ru.tbank.backend.dto.NoteTextDto;
import ru.tbank.backend.mapper.NoteMapper;
import ru.tbank.backend.repository.NoteRepository;
import ru.tbank.backend.service.NoteService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public NoteDto processText(NoteTextDto noteText) {
        return null;
    }

    @Override
    public List<NoteDto> getNotesByUserId(UUID userId) {
        var projections = noteRepository.findNote(UUID.fromString("0622339d-4868-4829-a681-ac50be2cf623"));
        var dtos = noteMapper.mergeProjections(projections);
        return null;
    }
}
