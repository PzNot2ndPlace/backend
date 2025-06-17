package ru.tbank.backend.mapper;

import org.springframework.stereotype.Component;
import ru.tbank.backend.dto.*;
import ru.tbank.backend.entity.*;
import ru.tbank.backend.enums.CategoryType;
import ru.tbank.backend.enums.TriggerType;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NoteMapper {
    private static final DateTimeFormatter DB_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSx");

    private static final DateTimeFormatter DB_DATE_FORMATTER_WITH_SECONDS =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssx");

    public NoteDtoWithTriggers mergeProjections(List<NoteProjection> projections) {
        if (projections.isEmpty()) {
            return null;
        }

        NoteProjection first = projections.get(0);

        NoteDtoWithTriggers.NoteDtoWithTriggersBuilder builder = NoteDtoWithTriggers.builder()
                .id(first.getId())
                .userId(first.getUserId())
                .text(first.getText())
                .createdAt(first.getCreatedAtAsOffset())
                .updatedAt(first.getUpdatedAtAsOffset())
                .categoryType(CategoryType.valueOf(first.getCategoryType()));

        List<TriggerDto> triggers = projections.stream()
                .map(this::mapToTriggerDto)
                .filter(Objects::nonNull)
                .toList();

        return builder.triggers(triggers).build();
    }

    private TriggerDto mapToTriggerDto(NoteProjection projection) {
        if (projection.getTriggerId() == null) {
            return null;
        }

        return switch (projection.getTriggerType()) {
            case TIME -> {
                try {
                    OffsetDateTime time = OffsetDateTime.parse(
                            projection.getTriggerValue(),
                            DB_DATE_FORMATTER
                    );
                    yield new TriggerTimeDto(
                            projection.getTriggerId(),
                            TriggerType.TIME,
                            projection.getIsReady(),
                            time
                    );
                } catch (DateTimeParseException ex) {
                    try {
                        OffsetDateTime time = OffsetDateTime.parse(
                                projection.getTriggerValue(),
                                DB_DATE_FORMATTER_WITH_SECONDS
                        );
                        yield new TriggerTimeDto(
                                projection.getTriggerId(),
                                TriggerType.TIME,
                                projection.getIsReady(),
                                time
                        );
                    } catch (DateTimeParseException e) {
                        throw new RuntimeException(
                                "Failed to parse trigger time: " + projection.getTriggerValue(),
                                e
                        );
                    }
                }
            }
            case LOCATION -> new TriggerLocationDto(
                    projection.getTriggerId(),
                    TriggerType.LOCATION,
                    projection.getIsReady(),
                    projection.getTriggerValue()
            );
            default -> throw new IllegalArgumentException(
                    "Unknown trigger type: " + projection.getTriggerType()
            );
        };
    }

    public NoteDto mapToNoteDto(NoteProjection projection) {
        return NoteDto.builder()
                .id(projection.getId())
                .userId(projection.getUserId())
                .text(projection.getText())
                .createdAt(projection.getCreatedAtAsOffset())
                .updatedAt(projection.getUpdatedAtAsOffset())
                .categoryType(CategoryType.valueOf(projection.getCategoryType()))
                .trigger(mapToTriggerDto(projection))
                .build();
    }

    public List<NoteDtoWithTriggers> mapToNoteDtoWithTriggers(List<NoteProjection> projections) {
        Map<UUID, List<NoteProjection>> groupedProjections = projections.stream()
                .collect(Collectors.groupingBy(NoteProjection::getId));

        return groupedProjections.values().stream()
                .map(this::mapGroupToNoteDtoWithTriggers)
                .toList();
    }

    public NoteDtoWithTriggers mapGroupToNoteDtoWithTriggers(List<NoteProjection> group) {
        if (group.isEmpty()) {
            return null;
        }

        NoteProjection first = group.get(0);

        List<TriggerDto> triggers = group.stream()
                .map(this::mapToTriggerDto)
                .filter(Objects::nonNull)
                .toList();

        return NoteDtoWithTriggers.builder()
                .id(first.getId())
                .userId(first.getUserId())
                .text(first.getText())
                .createdAt(first.getCreatedAtAsOffset())
                .updatedAt(first.getUpdatedAtAsOffset())
                .categoryType(CategoryType.valueOf(first.getCategoryType()))
                .triggers(triggers)
                .build();
    }

    public NoteDtoWithTriggersResponse mapToNoteDtoWithTriggersResponse(
            NoteEntity note,
            List<NoteTriggerEntity> noteTriggerEntities,
            List<TriggerEntity> triggerEntities,
            GptResponse gptResponse
    ) {
        Map<UUID, NoteTriggerEntity> triggerIdToNoteTrigger = noteTriggerEntities.stream()
                .collect(Collectors.toMap(NoteTriggerEntity::getTriggerId, Function.identity()));

        List<TriggerDto> triggerDtos = triggerEntities.stream()
                .map(triggerEntity -> {
                    NoteTriggerEntity noteTrigger = triggerIdToNoteTrigger.get(triggerEntity.getId());
                    if (noteTrigger == null) {
                        return null;
                    }

                    if (triggerEntity instanceof TriggerTimeEntity timeEntity) {
                        return new TriggerTimeDto(
                                triggerEntity.getId(),
                                noteTrigger.getTriggerType(),
                                noteTrigger.getIsReady(),
                                timeEntity.getTime()
                        );
                    } else if (triggerEntity instanceof TriggerLocationEntity locationEntity) {
                        return new TriggerLocationDto(
                                triggerEntity.getId(),
                                noteTrigger.getTriggerType(),
                                noteTrigger.getIsReady(),
                                locationEntity.getLocation()
                        );
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        NoteDtoWithTriggers noteDto = NoteDtoWithTriggers.builder()
                .id(note.getId())
                .userId(note.getUserId())
                .text(note.getText())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .categoryType(note.getCategoryType())
                .triggers(triggerDtos)
                .build();

        return NoteDtoWithTriggersResponse.builder()
                .noteDto(noteDto)
                .status(gptResponse.getStatus())
                .message(gptResponse.getMessage())
                .build();
    }
}