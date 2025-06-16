package ru.tbank.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель для ошибки валидации")
public class CustomFieldError {
    @Schema(description = "Поле, в котором допущена ошибка")
    private String field;

    @Schema(description = "Сообщение об ошибке")
    private String message;
}