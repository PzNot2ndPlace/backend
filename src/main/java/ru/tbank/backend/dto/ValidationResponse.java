package ru.tbank.backend.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ на запрос с ошибками валидации")
public class ValidationResponse {
    @Schema(description = "Статус ответа", example = "400")
    private int status;

    @Schema(description = "Время ответа", example = "2024-06-18T07:07:35.122Z")
    private LocalDateTime timestamp;

    @Schema(description = "Сообщение ответа", example = "Ошибка валидации")
    private String message;

    @ArraySchema(schema = @Schema(implementation = CustomFieldError.class))
    private List<CustomFieldError> errors;
}