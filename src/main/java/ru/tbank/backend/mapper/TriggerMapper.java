package ru.tbank.backend.mapper;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tbank.backend.dto.TriggerDto;
import ru.tbank.backend.dto.TriggerLocationDto;
import ru.tbank.backend.dto.TriggerTimeDto;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TriggerMapper {
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerSubtypes(TriggerTimeDto.class, TriggerLocationDto.class);
    }

    public List<TriggerDto> mapTriggers(String triggerData) {
        if (triggerData == null || triggerData.isBlank()) {
            return Collections.emptyList();
        }
        try {
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(
                    List.class,
                    TriggerDto.class
            );
            return objectMapper.readValue(triggerData, type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse triggers: " + e.getMessage(), e);
        }
    }
}