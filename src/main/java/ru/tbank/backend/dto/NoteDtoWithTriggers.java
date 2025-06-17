package ru.tbank.backend.dto;

import lombok.Builder;
import lombok.Data;
import ru.tbank.backend.enums.CategoryType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class NoteDtoWithTriggers {

    private UUID id;

    private UUID userId;

    private String text;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private CategoryType categoryType;

    private List<TriggerDto> triggers;

}
