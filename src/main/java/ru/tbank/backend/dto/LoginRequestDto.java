package ru.tbank.backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequestDto {

    private String email;

    private String password;

}
