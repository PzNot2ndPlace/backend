package ru.tbank.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GptTriggerResponse {

    private String triggerType;

    private String triggerValue;

}
