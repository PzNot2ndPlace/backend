package ru.tbank.backend.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DateTimeParser {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final ZoneOffset FIXED_OFFSET = ZoneOffset.ofHours(7);

    public static OffsetDateTime parse(String dateTimeString) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, FORMATTER);

            return OffsetDateTime.of(localDateTime, FIXED_OFFSET);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid date-time format. Expected format: yyyy-MM-dd HH:mm", e);
        }
    }
}