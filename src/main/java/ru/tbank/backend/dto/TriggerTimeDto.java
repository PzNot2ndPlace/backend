package ru.tbank.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.tbank.backend.enums.TriggerType;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TriggerTimeDto extends TriggerDto {

    private OffsetDateTime time;

    public TriggerTimeDto(UUID triggerId, TriggerType triggerType, Boolean isReady, OffsetDateTime time) {
        this.setId(triggerId);
        this.setTriggerType(triggerType);
        this.setIsReady(isReady);
        this.setTime(time);
    }
}
