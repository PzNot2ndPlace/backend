package ru.tbank.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Context {

    private String text;
    private String createdAt;
    private String updatedAt;
    private String categoryType;
    private List<GptTriggerResponse> triggers;

}
