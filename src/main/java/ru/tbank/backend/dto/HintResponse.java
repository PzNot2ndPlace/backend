package ru.tbank.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HintResponse {

    private Context note;
    private String hintText;

}
