package ru.tbank.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tbank.backend.dto.GptResponse;
import ru.tbank.backend.dto.NoteDtoWithTriggers;
import ru.tbank.backend.dto.NoteTextDto;
import ru.tbank.backend.dto.TriggerDto;
import ru.tbank.backend.entity.NoteEntity;
import ru.tbank.backend.entity.NoteTriggerEntity;
import ru.tbank.backend.entity.TriggerEntity;
import ru.tbank.backend.enums.CategoryType;
import ru.tbank.backend.enums.TriggerType;
import ru.tbank.backend.factory.NoteTriggerEntityFactory;
import ru.tbank.backend.factory.TriggerEntityFactory;
import ru.tbank.backend.mapper.NoteMapper;
import ru.tbank.backend.repository.NoteRepository;
import ru.tbank.backend.repository.NoteTriggerRepository;
import ru.tbank.backend.repository.TriggerRepository;
import ru.tbank.backend.service.NoteService;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final TriggerRepository triggerRepository;
    private final NoteTriggerRepository noteTriggerRepository;
    private final NoteMapper noteMapper;

    @Transactional(readOnly = true)
    @Override
    public NoteDtoWithTriggers processText(NoteTextDto noteText) {

        //TODO отправка на LLM на обработку при получении сохранить и отправить пользователю

        var projections = noteRepository.findNote(UUID.fromString("0622339d-4868-4829-a681-ac50be2cf623"));
        return noteMapper.mergeProjections(projections);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NoteDtoWithTriggers> getNotesByUserId(UUID userId) {
        var projections = noteRepository.findByUserId(userId);
        return noteMapper.mapToNoteDtoWithTriggers(projections);
    }

    @Transactional
    @Override
    public NoteDtoWithTriggers handleNote(GptResponse response, UUID userId) {
        List<TriggerEntity> triggerEntities = new ArrayList<>();
        List<NoteTriggerEntity> noteTriggerEntities = new ArrayList<>();

        var noteEntity = new NoteEntity(
                UUID.randomUUID(),
                userId,
                response.getText(),
                OffsetDateTime.now(),
                null,
                CategoryType.valueOf(response.getCategoryType().toUpperCase())
        );

        for (var trigger : response.getTriggers()) {
            var triggerType = TriggerType.valueOf(trigger.getTriggerType().toUpperCase());
            TriggerEntity triggerEntity = TriggerEntityFactory.createTriggerEntity(
                    triggerType,
                    UUID.randomUUID(),
                    trigger.getTriggerValue()
            );
            triggerEntities.add(triggerEntity);

            noteTriggerEntities.add(NoteTriggerEntityFactory.createNoteTriggerEntity(
                    noteEntity.getId(), triggerEntity.getId(), trigger
            ));
        }

        triggerRepository.saveAll(triggerEntities);
        noteRepository.save(noteEntity);
        noteTriggerRepository.saveAll(noteTriggerEntities);

        var projections = noteRepository.findNote(noteEntity.getId());
        return noteMapper.mergeProjections(projections);
    }
}
