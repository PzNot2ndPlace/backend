package ru.tbank.backend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tbank.backend.enums.TriggerType;

import java.util.UUID;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "triggerType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TriggerTimeDto.class, name = "TIME"),
        @JsonSubTypes.Type(value = TriggerLocationDto.class, name = "LOCATION")
})
public abstract class TriggerDto {
    private UUID id;
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "triggerType")
    private TriggerType triggerType;
    private Boolean isReady;
}
