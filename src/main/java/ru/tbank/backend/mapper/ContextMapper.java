package ru.tbank.backend.mapper;

import ru.tbank.backend.dto.Context;
import ru.tbank.backend.dto.ContextList;
import ru.tbank.backend.dto.GptTriggerResponse;
import ru.tbank.backend.dto.NoteProjection;
import ru.tbank.backend.enums.TriggerType;
import ru.tbank.backend.utils.CaseConverter;
import ru.tbank.backend.utils.DateTimeParser;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContextMapper {

    public static ContextList mapToContextList(List<NoteProjection> notes) {
        return ContextList.builder()
                .context(mapNotesToContexts(notes))
                .current_time(DateTimeParser.formatOffsetDateTime(OffsetDateTime.now()))
                .build();
    }

    private static List<Context> mapNotesToContexts(List<NoteProjection> notes) {
        return notes.stream()
                .map(ContextMapper::mapNoteToContext)
                .collect(Collectors.toList());
    }

    private static Context mapNoteToContext(NoteProjection note) {
        return Context.builder()
                .text(note.getText())
                .createdAt(DateTimeParser.formatOffsetDateTime(note.getCreatedAtAsOffset()))
                .updatedAt(
                        note.getUpdatedAtAsOffset() == null ? null
                                : DateTimeParser.formatOffsetDateTime(note.getUpdatedAtAsOffset())
                )
                .categoryType(CaseConverter.toNormalCase(note.getCategoryType()))
                .triggers(createTriggerList(note))
                .build();
    }

    private static List<GptTriggerResponse> createTriggerList(NoteProjection note) {
        if (note.getTriggerId() == null) {
            return Collections.emptyList();
        }

        if (note.getTriggerType().equals(TriggerType.TIME)) {
            return Collections.singletonList(
                    new GptTriggerResponse(
                            CaseConverter.toNormalCase(note.getTriggerType().toString()),
                            DateTimeParser.formatOffsetDateTime(
                                    DateTimeParser.convertStringToOffsetTime(note.getTriggerValue())
                            )
                    )
            );
        } else {
            return Collections.singletonList(
                    new GptTriggerResponse(
                            CaseConverter.toNormalCase(note.getTriggerType().toString()),
                            note.getTriggerValue()
                    )
            );
        }
    }
}