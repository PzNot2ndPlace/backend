package ru.tbank.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.tbank.backend.enums.TriggerType;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TriggerLocationDto extends TriggerDto {

    private String location;

    public TriggerLocationDto(UUID triggerId, TriggerType triggerType, Boolean isReady, String triggerValue) {
        this.setId(triggerId);
        this.setTriggerType(triggerType);
        this.setIsReady(isReady);
        this.setLocation(triggerValue);
    }
}
