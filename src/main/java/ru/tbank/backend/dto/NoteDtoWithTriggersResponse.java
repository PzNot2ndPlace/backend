package ru.tbank.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteDtoWithTriggersResponse {

    private NoteDtoWithTriggers noteDto;

    private String status;

    private String message;

}
