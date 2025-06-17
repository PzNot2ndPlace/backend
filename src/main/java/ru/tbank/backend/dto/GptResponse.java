package ru.tbank.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GptResponse {

    private String text;

    private String categoryType;

    private List<GptTriggerResponse> triggers;

}
