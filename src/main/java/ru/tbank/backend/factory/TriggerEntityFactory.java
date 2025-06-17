package ru.tbank.backend.factory;

import ru.tbank.backend.dto.TriggerDto;
import ru.tbank.backend.dto.TriggerLocationDto;
import ru.tbank.backend.entity.TriggerEntity;
import ru.tbank.backend.entity.TriggerLocationEntity;
import ru.tbank.backend.entity.TriggerTimeEntity;
import ru.tbank.backend.enums.TriggerType;
import ru.tbank.backend.utils.DateTimeParser;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TriggerEntityFactory {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static TriggerEntity createTriggerEntity(
            TriggerType triggerType,
            UUID id,
            String triggerValue
    ) {
        try {
            switch (triggerType) {
                case TIME:
                    OffsetDateTime time = DateTimeParser.parse(triggerValue);
                    return new TriggerTimeEntity(id, time);

                case LOCATION:
                    return new TriggerLocationEntity(id, triggerValue);

                default:
                    throw new IllegalArgumentException("Unsupported trigger type: " + triggerType);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create TriggerEntity for type " + triggerType, e);
        }
    }
}