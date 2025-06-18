package ru.tbank.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContextList {

    private List<Context> context;
    private String current_time;

}
