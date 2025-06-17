package ru.tbank.backend.factory;

import ru.tbank.backend.dto.GptTriggerResponse;
import ru.tbank.backend.entity.NoteTriggerEntity;
import ru.tbank.backend.entity.TriggerEntity;
import ru.tbank.backend.entity.TriggerLocationEntity;
import ru.tbank.backend.entity.TriggerTimeEntity;
import ru.tbank.backend.enums.TriggerType;
import ru.tbank.backend.utils.DateTimeParser;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class NoteTriggerEntityFactory {
    public static NoteTriggerEntity createNoteTriggerEntity(UUID noteId, UUID triggerId, GptTriggerResponse trigger) {
        return new NoteTriggerEntity(
                noteId,
                triggerId,
                TriggerType.valueOf(trigger.getTriggerType().toUpperCase()),
                false
        );
    }
}