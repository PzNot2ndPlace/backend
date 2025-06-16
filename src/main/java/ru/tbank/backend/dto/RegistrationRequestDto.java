package ru.tbank.backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistrationRequestDto {

    private String email;

    private String fullName;

    private String password;

}
