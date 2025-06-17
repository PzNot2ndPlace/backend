package ru.tbank.backend.dto;

import ru.tbank.backend.enums.TriggerType;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public interface NoteProjection {
    UUID getId();
    UUID getUserId();
    String getText();
    Instant getCreatedAt();
    Instant getUpdatedAt();
    String getCategoryType();
    UUID getTriggerId();
    TriggerType getTriggerType();
    Boolean getIsReady();
    String getTriggerValue();

    default OffsetDateTime getCreatedAtAsOffset() {
        return getCreatedAt() != null ? OffsetDateTime.ofInstant(getCreatedAt(), ZoneId.systemDefault()) : null;
    }

    default OffsetDateTime getUpdatedAtAsOffset() {
        return getUpdatedAt() != null ? OffsetDateTime.ofInstant(getUpdatedAt(), ZoneId.systemDefault()) : null;
    }
}
