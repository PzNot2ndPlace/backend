package ru.tbank.backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tbank.backend.entity.TriggerEntity;
import ru.tbank.backend.enums.CategoryType;
import ru.tbank.backend.utils.CategoryTypeConverter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class NoteDto {

    private UUID id;

    private UUID userId;

    private String text;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private CategoryType categoryType;

    private TriggerDto trigger;

}
