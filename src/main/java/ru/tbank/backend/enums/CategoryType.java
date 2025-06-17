package ru.tbank.backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryType {

    TIME("time"),
    LOCATION("location"),
    EVENT("event"),
    SHOPPING("shopping"),
    CALL("call"),
    MEETING("meeting"),
    DEADLINE("deadline"),
    HEALTH("health"),
    OTHER("other");

    private final String value;

    CategoryType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CategoryType fromValue(String value) {
        for (CategoryType status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Неизвестное значение: " + value);
    }

}
